package com.ttn.demo.domain.letter.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ttn.demo.domain.letter.dao.LetterRepository;
import com.ttn.demo.domain.letter.domain.Letter;
import com.ttn.demo.domain.letter.dto.request.LetterCreateRequest;
import com.ttn.demo.domain.letter.dto.response.LetterResponse;
import com.ttn.demo.domain.letter.dto.response.LetterSummaryResponse;
import com.ttn.demo.domain.letter.exception.LetterNotFoundException;
import com.ttn.demo.domain.letter.exception.NoAvailableReceiverException;
import com.ttn.demo.domain.letter.exception.SenderNotFoundException;
import com.ttn.demo.domain.user.dao.UserRepository;
import com.ttn.demo.domain.user.domain.User;
import com.ttn.demo.domain.user.exception.UserNotFoundException;
import com.ttn.demo.infra.papagoTranslation.PapagoApiClient;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LetterService {
    @Value("${papago.api-url}")
    private String apiUrl;

    @Value("${papago.client-id}")
    private String clientId;

    @Value("${papago.client-secret}")
    private String clientSecret;

    private final LetterRepository letterRepository;
    private final UserRepository userRepository;
    private final PapagoApiClient papagoApiClient;

    public void createLetter(Long id, LetterCreateRequest letterRequestDTO) {
        User sender = userRepository.findById(id)
                .orElseThrow(SenderNotFoundException::new);

        List<User> allUsers = userRepository.findAll().stream()
                .filter(user -> !user.getId().equals(sender.getId()))
                .toList();

        if (allUsers.isEmpty()) {
            throw new NoAvailableReceiverException();
        }

        User receiver = allUsers.get(new Random().nextInt(allUsers.size()));

        Letter senderLetter = Letter.of(sender, receiver, letterRequestDTO.getTitle(), letterRequestDTO.getContents(), sender.getLanguage()).build();
        letterRepository.save(senderLetter);

        if(!sender.getLanguage().equals(receiver.getLanguage())) {
            String translatedContents = traslateContents(sender.getLanguage(), receiver.getLanguage(),letterRequestDTO.getContents());
            String translatedTitle = traslateContents(sender.getLanguage(), receiver.getLanguage(),letterRequestDTO.getTitle());
            Letter recieverLetter = Letter.of(sender, receiver, translatedTitle, translatedContents, receiver.getLanguage()).build();
            letterRepository.save(recieverLetter);
        }
    }

    public void createLetter(User sender, User receiver, String title, String contents) {
        Letter senderLetter = Letter.of(sender, receiver, title, contents, sender.getLanguage()).build();
        letterRepository.save(senderLetter);

        if (!sender.getLanguage().equals(receiver.getLanguage())) {
            String translatedContents = traslateContents(sender.getLanguage(), receiver.getLanguage(),contents);
            String translatedTitle = traslateContents(sender.getLanguage(), receiver.getLanguage(),title);
            Letter receiverLetter = Letter.of(sender, receiver, translatedTitle, translatedContents, receiver.getLanguage()).build();
            letterRepository.save(receiverLetter);
        }
    }

    public LetterResponse getLetterById(Long id, Long letterId) {
        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new LetterNotFoundException(letterId));

        if(letter.getReceiver().getId().equals(id) && letter.getOpenedAt() != null) {
            letter.setOpenedAt(LocalDateTime.now());
            letterRepository.save(letter);
        }
        return LetterResponse.of(letter);
    }

    public List<LetterSummaryResponse> getAllSentLetters(Long id) {
        String language = userRepository.findLanguageById(id)
                .orElseThrow(UserNotFoundException::new);

        return letterRepository.findBySenderIdAndLanguage(id, language).stream()
                .map(LetterSummaryResponse::of)
                .collect(Collectors.toList());
    }

    public List<LetterSummaryResponse> getAllReceivedLetters(Long id) {
        String language = userRepository.findLanguageById(id)
                .orElseThrow(UserNotFoundException::new);

        return letterRepository.findByReceiverIdAndLanguage(id, language).stream()
                .map(LetterSummaryResponse::of)
                .collect(Collectors.toList());
    }

    private String traslateContents(String source, String target, String contents){
        Map<String, String> languageMap = Map.of(
                "us", "en",
                "jp", "ja",
                "kr","ko"
        );
        source = languageMap.getOrDefault(source, source);
        target = languageMap.getOrDefault(target, target);

        ObjectNode rootNode = getJsonNodes(source,target,contents);
        JSONObject jsonResponse = sendReqeust(rootNode);

        return jsonResponse
                .getJSONObject("message")
                .getJSONObject("result")
                .getString("translatedText");
    }

    private JSONObject sendReqeust(ObjectNode rootNode) {
        String requestBody =  rootNode.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("X-NCP-APIGW-API-KEY-ID", clientId);
        headers.put("X-NCP-APIGW-API-KEY", clientSecret);

        String response = papagoApiClient.sendRequest(apiUrl, headers, requestBody);
        return new JSONObject(response);
    }

    private static ObjectNode getJsonNodes(String source, String target, String contents) {
        ObjectMapper objectMapper = new ObjectMapper();

        // Root JSON Object
        ObjectNode rootNode = objectMapper.createObjectNode();

        // Populate JSON fields
        rootNode.put("source", source);
        rootNode.put("target", target);
        rootNode.put("text", contents);
        return rootNode;
    }
}
