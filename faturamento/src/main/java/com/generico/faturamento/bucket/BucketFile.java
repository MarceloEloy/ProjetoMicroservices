package com.generico.faturamento.bucket;

import org.springframework.http.MediaType;

import java.awt.*;
import java.io.InputStream;

public record BucketFile (String name, InputStream is, MediaType type, Long size){}
