package com.social.moinda.core.domains.upload.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum FileExtension {

    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    TXT("txt"),
    ;

    private final String extension;

    public static boolean isValidExtension(String fileExtension) {
        return extensionNames()
                .stream()
                .anyMatch(extension -> extension.equalsIgnoreCase(fileExtension));
    }

    private static List<String> extensionNames() {
        return Arrays.stream(values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
