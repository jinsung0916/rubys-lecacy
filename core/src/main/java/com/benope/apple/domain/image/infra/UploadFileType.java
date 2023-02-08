package com.benope.apple.domain.image.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.tika.Tika;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
public enum UploadFileType {

	IMAGE, AUDIO;

	public static String getDirNameByFileType(final String fileName) {
		return UploadFileType.of(fileName).name().toLowerCase();
	}

	private static UploadFileType of(final String fileName) {
		if (isImage(fileName)) {
			return IMAGE;
		}

		if (isAudio(fileName)) {
			return AUDIO;
		}

		throw new IllegalArgumentException(String.format("허용되지 않은 타입의 파일입니다. [요청: %s]", fileName));
	}

	private static String extractExtension(final String originalFileName) {
		final String FILENAME_EXTENSION_DELIMITER = ".";
		final String FILE_PARAMETER = "?";

		String fileName = originalFileName;

		if (fileName.lastIndexOf(FILE_PARAMETER) > 0) {
			fileName = fileName.substring(0, fileName.lastIndexOf(FILE_PARAMETER));
		}
		return fileName.substring(fileName.lastIndexOf(FILENAME_EXTENSION_DELIMITER) + 1).toLowerCase();
	}

	private static boolean isAudio(final String fileName) {
		final Set<String> AUDIO_EXTENSIONS = new HashSet<>(Arrays.asList("aac", "wmv", "mp3", "m4a", "wma"));
		return AUDIO_EXTENSIONS.contains(extractExtension(fileName));
	}

	private static boolean isImage(final String fileName) {
		final MimeType mimeType = MimeTypeUtils.parseMimeType(new Tika().detect(fileName));
		return MimeTypeUtils.IMAGE_GIF.equals(mimeType) ||
			MimeTypeUtils.IMAGE_JPEG.equals(mimeType) ||
			MimeTypeUtils.IMAGE_PNG.equals(mimeType);
	}

}
