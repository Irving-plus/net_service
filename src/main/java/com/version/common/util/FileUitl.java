package com.version.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

public class FileUitl {
	public static String readAll(String fileName) throws FileNotFoundException, IOException {
		String filePath = FileUitl.class.getClassLoader().getResource(fileName).getPath();
		return IOUtils.toString(new FileInputStream(new File(filePath)), Charset.defaultCharset());
	}
}
