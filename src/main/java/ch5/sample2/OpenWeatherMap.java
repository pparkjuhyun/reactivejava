package ch5.sample2;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import network.OkHttpHelper;
import utils.CommonUtils;
import utils.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenWeatherMap {
    private static final String URL =
            "http://api.openweathermap.org/data/2.5/weather?q=London&APPID=";
    private static final String API_KEY =
            "f560eb9227f5daa66ed77cc61581ebbb";

    public void run() {
        Observable<String> source = Observable.just(URL + API_KEY)
                .map(OkHttpHelper::getWithLog)
                .subscribeOn(Schedulers.io());

        Observable<String> temperature = source.map(this::parseTemperature);
        Observable<String> city = source.map(this::parseCityName);
        Observable<String> country = source.map(this::parseCountry);

        CommonUtils.exampleStart();

        Observable.concat(temperature, city, country)
                .observeOn(Schedulers.newThread())
                .subscribe(Log::it);

        CommonUtils.sleep(1000);
    }

    private String parseTemperature(String json) {
        return parse(json, "\"temp\":[0-9]*.[0-9]*");
    }

    private String parseCityName(String json) {
        return parse(json, "\"name\":\"[a-zA-Z]*\"");
    }

    private String parseCountry(String json) {
        return parse(json, "\"country\":\"[a-zA-Z]*\"");
    }

    private String parse(String json, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(json);
        if (match.find()) {
            return match.group();
        }
        return "N/A";
    }
}
