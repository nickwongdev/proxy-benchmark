package com.yskts.pb.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class Constants {

	public static final String SERVER_HOST_PROP_VAR = "server.host";
	public static final String SERVER_PORT_PROP_VAR = "server.port";
	public static final String PROXY_HOST_PROP_VAR = "proxy.host";
	public static final String PROXY_PORT_PROP_VAR = "proxy.port";
	public static final String INITIAL_CONNECTIONS_PROP_VAR = "initial.connections";
	public static final String MAX_CONNECTIONS_PROP_VAR = "max.connections";
	public static final String INCREMENT_NUM_PROP_VAR = "increment.num";
	public static final String INCREMENT_TIME_PROP_VAR = "increment.time";
	public static final String TEST_DURATION_PROP_VAR = "test.duration";

	public static final String SERVER_HOST = System.getProperty(SERVER_HOST_PROP_VAR, "localhost");
	public static final String PROXY_HOST = System.getProperty(PROXY_HOST_PROP_VAR, "localhost");

	public static final int PROXY_PORT = 8080;
	public static final int SERVER_PORT = 9090;

	private static final byte[] END_LINE = "\n".getBytes(Charset.forName("UTF-8"));

	private static final String REQUEST_PAYLOAD = "{\n" +
			"\t\"accountId\": 12345,\n" +
			"\t\"authorization\": \"f2117cce-f98a-4f06-8a96-10a12a21046c\",\n" +
			"\t\"client\": \"e7a3ddeb-01dc-4176-8ba4-0c6dde4969c2\"\n" +
			"}";
	private static final byte[] REQUEST_PAYLOAD_BYTES = REQUEST_PAYLOAD.getBytes(Charset.forName("UTF-8"));
	private static final byte[] REQUEST_HEADER = ("POST /pb HTTP/1.1\n" +
			"Host: " + PROXY_HOST +
			"Content-Length: " + REQUEST_PAYLOAD_BYTES.length + "\n" +
			"Content-Type: application/json\n").getBytes(Charset.forName("UTF-8"));

	public static final ByteBuf UNRELEASABLE_REQUEST_BUFFER =
			Unpooled.unreleasableBuffer(
					Unpooled.wrappedBuffer(
							REQUEST_HEADER,
							END_LINE,
							REQUEST_PAYLOAD_BYTES,
							END_LINE));

	private static final String RESPONSE_PAYLOAD = "{\n" +
			"\t\"id\": 104932,\n" +
			"\t\"name\": \"The Great Gambini\",\n" +
			"\t\"type\": \"PLAYER\",\n" +
			"\t\"address\": {\n" +
			"\t\t\"street\": \"1234 Main St. SW\",\n" +
			"\t\t\"city\": \"Irvine\",\n" +
			"\t\t\"state\": \"CA\",\n" +
			"\t\t\"zip\": 92618,\n" +
			"\t\t\"country\": \"USA\"\n" +
			"\t},\n" +
			"\t\"games\": [\n" +
			"\t\t{\n" +
			"\t\t\t\"gameId\": 90431,\n" +
			"\t\t\t\"region\": \"US\",\n" +
			"\t\t\t\"purchaseDate\": \"2011-01-01\"\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"gameId\": 93,\n" +
			"\t\t\t\"region\": \"US\",\n" +
			"\t\t\t\"purchaseDate\": \"1999-11-01\"\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"gameId\": 1123,\n" +
			"\t\t\t\"region\": \"US\",\n" +
			"\t\t\t\"purchaseDate\": \"2001-09-21\"\n" +
			"\t\t}\n" +
			"\t],\n" +
			"\t\"licenses\": [\n" +
			"\t\t{\n" +
			"\t\t\t\"licenseId\": 4938,\n" +
			"\t\t\t\"dateAdded\": \"2004-03-12\"\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"licenseId\": 4893,\n" +
			"\t\t\t\"dateAdded\": \"2001-08-02\"\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"licenseId\": 4321,\n" +
			"\t\t\t\"dateAdded\": \"2002-02-22\"\n" +
			"\t\t}\n" +
			"\t],\n" +
			"\t\"notes\": [\n" +
			"\t\t{\n" +
			"\t\t\t\"noteId\": 4723472938,\n" +
			"\t\t\t\"date\": \"1999-01-02\",\n" +
			"\t\t\t\"note\": \"Customer called in for help creating account. Feedback sent to account creation team.\"\n" +
			"\t\t},\n" +
			"\t\t{\n" +
			"\t\t\t\"noteId\": 19938271234,\n" +
			"\t\t\t\"date\": \"2018-07-01\",\n" +
			"\t\t\t\"note\": \"Customer called in with a login issue with game 12. Game account reset. Login checked.\"\n" +
			"\t\t}\n" +
			"\t]\n" +
			"}";

	private static final byte[] RESPONSE_PAYLOAD_BYTES = RESPONSE_PAYLOAD.getBytes(Charset.forName("UTF-8"));
	private static final byte[] RESPONSE_HEADER = ("HTTP/1.1 200 OK\n" +
			"Content-Length: " + RESPONSE_PAYLOAD_BYTES.length + "\n" +
			"Content-Type: application/json\n").getBytes(Charset.forName("UTF-8"));

	public static final ByteBuf UNRELEASABLE_RESPONSE_BUFFER =
			Unpooled.unreleasableBuffer(
					Unpooled.wrappedBuffer(
							RESPONSE_HEADER,
							END_LINE,
							RESPONSE_PAYLOAD_BYTES,
							END_LINE));


}
