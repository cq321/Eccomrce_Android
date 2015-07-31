package com.onjection.ServerTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.onjection.opencart.Utilz.Urls;
import com.onjection.opencart.Utilz.Constant;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ServerDownload extends AsyncTask<String, Void, JSONObject> {

	InputStream is = null;
	JSONObject jObj = null;
	String json = "";
	Context mContext;
	ServerResponse mResponse;
	String tagResult, parameters;
	int responceCode; // / this code is use to chk respone

	public ServerDownload(Context context, String parametrs,
			ServerResponse serverResponse, String tagResult) {
		// TODO Auto-generated constructor stub
		mContext = context;
		this.parameters = parametrs;
		mResponse = serverResponse;
		this.tagResult = tagResult;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		// TODO Auto-generated method stub

		if (params[0] == null) {
			// getJSONFromUrl(Constant.JSON_URL + parameters, null,
			// HttpMethod.GET);

		} else {
			if (tagResult.equals(Constant.tagSlideview)) {
				getJSONFromUrl(Urls.SlideShowuApi, params[0], HttpMethod.GET);
			} else if (tagResult.equals(Constant.tagLogin)) {
				getJSONFromUrl(Urls.login, params[0], HttpMethod.POST);
			} else if (tagResult.equals(Constant.tagregistraion)) {
				getJSONFromUrl(Urls.Registraionapi, params[0], HttpMethod.POST);
			} else if (tagResult.equals(Constant.ContactUs)) {
				getJSONFromUrl(Urls.ContactUs, params[0], HttpMethod.POST);
			} else if (tagResult.equals(Constant.tagmycart)) {
				getJSONFromUrl(Urls.GetAddTOCardpostApi, params[0],
						HttpMethod.POST);
			} else if (tagResult.equals(Constant.AddNewAddresstag)) {
				getJSONFromUrl(Urls.InsertNewAddressApi, params[0],
						HttpMethod.POST);
			} else if (tagResult.equals(Constant.UpdateNewAddresstag)) {
				getJSONFromUrl(Urls.EditAddressApi, params[0], HttpMethod.POST);
			} else if (tagResult.equals(Constant.PlaceOrderString)) {
				getJSONFromUrl(Urls.PlaceOrderApi, params[0], HttpMethod.POST);
			} else if (tagResult.equals(Constant.ShipingString)) {
				getJSONFromUrl(Urls.ShipingApi, params[0], HttpMethod.POST);
			} else if (tagResult.equals(Constant.forgotpassword)) {
				getJSONFromUrl(Urls.forgot, params[0], HttpMethod.POST);
			} else if (tagResult.equals(Constant.tagAuthentication)) {
				getJSONFromUrl(Urls.Authentication, params[0], HttpMethod.POST);
			} else if (tagResult.equals(Constant.Confirmorder)) {
				getJSONFromUrl(Urls.ConfirmOrderApi, params[0], HttpMethod.POST);
			}else if (tagResult.equals(Constant.ConfirmSucess)) {
				getJSONFromUrl(Urls.ConfrimSucessFullPayment, params[0], HttpMethod.POST);
			}
		}
		return jObj;
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		mResponse.httpResponse(result, tagResult, responceCode);
	}

	public JSONObject getJSONFromUrl(String url, String params,

	HttpMethod method) {

		// Making HTTP request
		try {
			// defaultHttpClient
			// DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpClient httpClient = getNewHttpClient();
			HttpResponse httpResponse = null;
			switch (method) {
			case POST:

				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new ByteArrayEntity(params.getBytes()));

				// httpPost.setEntity(new UrlEncodedFormEntity());
				httpPost.setHeader("Content-Type", "application/json");

				httpPost.setHeader("Accept", "application/json");
				httpResponse = httpClient.execute(httpPost);
				break;
			case PUT:
				HttpPut httpPut = new HttpPut(url);
				httpPut.setEntity(new ByteArrayEntity(params.getBytes()));
				httpResponse = httpClient.execute(httpPut);
				break;
			case DELETE:
				HttpDelete httpDelete = new HttpDelete(url);
				httpResponse = httpClient.execute(httpDelete);
				break;
			case GET:
				if (params != null && params.length() > 0) {
					if (url.indexOf("?") > 0) {
						if (url.indexOf("?") != url.length() - 1) {
							url = url + "&";

						}
					} else {
						url = url + "?";
					}
					url = url + new ByteArrayEntity(params.getBytes());
				}
				URLEncoder.encode(url, "UTF-8");
				HttpGet httpGet = new HttpGet(url);
				// httpGet.setHeader("Content-Type", "application/json");
				// httpGet.setHeader("Accept", "application/json");
				// Authorization: ApiKey username:12345
				// httpGet.setHeader("Authorization",
				// "ApiKey api+mobilev1@feedsme.com:a8ca1bdee52bec875709574a2bce3101996093a4");
				httpResponse = httpClient.execute(httpGet);

				break;
			case GETU:
				if (params != null && params.length() > 0) {
					if (url.indexOf("?") > 0) {
						if (url.indexOf("?") != url.length() - 1) {
							url = url + "&";

						}
					} else {
						url = url + "?";
					}
					url = url + new ByteArrayEntity(params.getBytes());
				}
				HttpGet httpGet1 = new HttpGet(url);
				httpGet1.setHeader("Content-Type", "application/json");
				httpGet1.setHeader("Authorization",
						"ApiKey api+mobilev1@feedsme.com:a8ca1bdee52bec875709574a2bce3101996093a4");
				httpGet1.setHeader("Accept", "application/json"); // Authorization:
																	// ApiKey
																	// username:12345
				// httpGet.setHeader("Authorization",
				// "ApiKey api+mobilev1@feedsme.com:a8ca1bdee52bec875709574a2bce3101996093a4");
				httpResponse = httpClient.execute(httpGet1);

				break;

			case PATCH:
				HttpPatch httpPatch = new HttpPatch(url);
				httpPatch.setEntity(new ByteArrayEntity(params.getBytes()));

				// httpPost.setEntity(new UrlEncodedFormEntity());
				httpPatch.setHeader("Content-Type", "application/json");
				httpPatch
						.setHeader("Authorization",
								"ApiKey api+mobilev1@feedsme.com:a8ca1bdee52bec875709574a2bce3101996093a4");
				httpPatch.setHeader("Accept", "application/json");
				httpResponse = httpClient.execute(httpPatch);

				break;
			default:
				return null;

			}
			responceCode = httpResponse.getStatusLine().getStatusCode();
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			Log.e("JSON", json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {

			jObj = new JSONObject(json);

		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}

	public class MySSLSocketFactory extends SSLSocketFactory {
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public MySSLSocketFactory(KeyStore truststore)
				throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};

			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port,
				boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host,
					port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}

	public HttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);

			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

}
