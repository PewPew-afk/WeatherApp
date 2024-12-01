import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import com.google.gson.Gson;
import java.util.Scanner;

public class WeatherApp {
	
	public static void main(String args[]) throws Exception{
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter your city: ");
		String city = scanner.next();
		scanner.close();
		
		String units = "imperial";
		String url = "https://pro.openweathermap.org/data/2.5/forecast?";
	
		//API_KEY saved as constant for security
		String urlCityQuery = url + "q=" + city + "&appid=" + Constants.API_KEY + "&units=" + units;
		
		HttpRequest postRequest = (HttpRequest) HttpRequest.newBuilder()
				.uri(new URI(urlCityQuery))
				.GET()
				.build();
		
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpResponse<String> postResponse = httpClient.send(postRequest, BodyHandlers.ofString());
		
		Gson gson = new Gson();
		WeatherInfo weatherInfo = new WeatherInfo();
		weatherInfo = gson.fromJson(postResponse.body(), WeatherInfo.class);
		WeatherInfo.WeatherData firstWeather = weatherInfo.getList().get(0);
		
		System.out.println("City: " + weatherInfo.getCity().getName() + ", " + weatherInfo.getCity().getCountry());
		System.out.println("Feels Like: " + firstWeather.getMain().getFeelsLike());
		System.out.println("Weather Description: " + firstWeather.getWeather().get(0).getDescription());
		
	}
}	
