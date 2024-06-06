package ru.rsreu.springhelloworld;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class HelloController {
    private int _id = 1;

    private final List<Map<String, String>> _data = new ArrayList<>() {{
        add(
            new HashMap<>() {{
                put("id", "1");
                put("message", "Hello world 1");
            }}
        );
        add(
            new HashMap<>() {{
                put("id", "2");
                put("message", "Hello world 2");
            }}
        );
    }};

    @GetMapping("/")
    public List<Map<String, String>> getData() {
        return _data;
    }

    @GetMapping("{parId}")
    public Optional<Map<String, String>> getMessage(@PathVariable String parId) {
        return _data.stream().filter(message -> message.get("id").equals(parId)).findFirst();
    }

    @PostMapping
    public Map<String, String> addMessage(@RequestBody Map<String, String> parMessage) {
        parMessage.put("id", String.valueOf(++_id));
        _data.add(parMessage);
        return parMessage;
    }
}
