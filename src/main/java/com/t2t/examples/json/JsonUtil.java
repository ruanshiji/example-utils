package com.t2t.examples.json;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * json相关处理类
 * 
 * @author ypf
 */
@SuppressWarnings(value = { "unchecked", "rawtypes", "unused" })
public class JsonUtil {

	private static Gson gson = null;

	static {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory());// 将null串转为""
		gson = builder.create();
	}

	public static String json(Object obj) {

		return getJson().toJson(obj);
	}

	public static <T> T unjson(String json, Class<T> z) {

		return getJson().fromJson(json, z);
	}

	public static Gson getJson() {

		return gson;
	}

	public static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {

		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

			Class<T> rawType = (Class<T>) type.getRawType();
			if (rawType == String.class) {
				return (TypeAdapter<T>) new StringAdapter();
			}
			if (rawType == Long.class) {
				return (TypeAdapter<T>) new LongAdapter();
			}
			if (rawType == Double.class) {
				return (TypeAdapter<T>) new DoubleAdapter();
			}
			return null;
		}
	}

	public static class StringAdapter extends TypeAdapter<String> {

		public String read(JsonReader reader) throws IOException {

			if (reader.peek() == JsonToken.NULL) {
				reader.nextNull();
				return "";
			}
			return reader.nextString();
		}

		public void write(JsonWriter writer, String value) throws IOException {

			if (value == null || "null".equalsIgnoreCase(value)) {
				writer.value("");
			} else {
				writer.value(value);
			}
		}
	}

	public static class LongAdapter extends TypeAdapter<Long> {

		public Long read(JsonReader reader) throws IOException {

			if (reader.peek() == JsonToken.NULL) {
				reader.nextNull();
				return null;
			}
			return reader.nextLong();
		}

		public void write(JsonWriter writer, Long value) throws IOException {

			if (value == null) {
				writer.value("");
			} else {
				writer.value(value);
			}
		}
	}

	public static class DoubleAdapter extends TypeAdapter<Double> {

		public Double read(JsonReader reader) throws IOException {

			if (reader.peek() == JsonToken.NULL) {
				reader.nextNull();
				return null;
			}
			return reader.nextDouble();
		}

		public void write(JsonWriter writer, Double value) throws IOException {

			if (value == null) {
				writer.value("");
			} else {
				writer.value(value);
			}
		}
	}

}
