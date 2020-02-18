import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class FirstExample {
	public void emit() {
		Observable.just(1, 2, 3, 4, 5, 6)
		.subscribe(System.out::println);
	}
	
	public void code2by2() {
		Observable<String> source = Observable.just("RED", "GREEN", "YELLOW");
		
		Disposable d = source.subscribe(
				v -> System.out.println("onNext() : value : " + v),
				err -> System.out.println("onError() : err : " + err.getMessage()),
				() -> System.out.println("onComplete()")
				);
		
		System.out.println("isDisposed(): " + d.isDisposed());
	}
	
	public void code2by3() {
		Observable<Integer> source = Observable.create(
				(ObservableEmitter<Integer> emitter) -> {
					emitter.onNext(100);
					emitter.onNext(200);
					emitter.onNext(300);
					emitter.onComplete();
				});
		
		source.subscribe(data -> System.out.println("Result: " +data));
	}
	
	//fromArray 예제
	public void code2by7() {
		Integer[] arr = {100, 200, 300};
		Observable<Integer> source = Observable.fromArray(arr);

		source.subscribe(System.out::println);
	}
	
	public void code2by10() {
		ArrayList<String> names = new ArrayList<String>();
		names.add("park");
		names.add("ju");
		names.add("hyun");
		
		Observable<String> source = Observable.fromIterable(names);

		source.subscribe(System.out::println);
	}
	
	public void code2by14() {
		Callable<String> callable = () -> {
			Thread.sleep(1000);
			return "hello Callable";
		};
		
		Observable<String> source = Observable.fromCallable(callable);
		source.subscribe(System.out::println);
	}
	
	public void code2by16() {
		Future<String> future = Executors.newSingleThreadExecutor().submit(() -> {
			Thread.sleep(1000);
			return "Hello Future";
		});
		
		Observable<String> source = Observable.fromFuture(future);
		source.subscribe(System.out::println);
	}
	
	public void code2by19() {
		Single<String> source = Single.just("Hello Single");
		source.subscribe(System.out::println);
	}
	
	//Observable -> Single 변환 방법
	public void code2by20() {
		Observable<String> source = Observable.just("Single.fromObservable()");
		Single.fromObservable(source).subscribe(System.out::println);
		
		Observable.just("Observable.single()")
		.single("default value")
		.subscribe(data -> System.out.println(data));
		
		String[] colors = {"fromArray, Observable.first()", "GREEN", "GOLD"};
		Observable.fromArray(colors)
		.first("default value")
		.subscribe(data -> System.out.println(data));
		
		Observable.empty()
		.single("Observable.empty()")
		.subscribe(data -> System.out.println(data));
	}
	
	public void code2by23() {
		AsyncSubject<String> subject = AsyncSubject.create();
		subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
		subject.onNext("1");
		subject.onNext("3");
		subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
		subject.onNext("5");
		subject.onComplete();
	}
	
	public void code2by24() {
		Float[] temperature = {10.1f, 13.4f, 12.5f};
		Observable<Float> source = Observable.fromArray(temperature);
		
		AsyncSubject<Float> subject = AsyncSubject.create();
		subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
		source.subscribe(subject);
	}
	
	public void code2by26() {
		BehaviorSubject<String> subject = BehaviorSubject.createDefault("6");
		subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
		subject.onNext("1");
		subject.onNext("3");
		subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
		subject.onNext("5");
		subject.onComplete();
	}
	
	public void code2by27() {
		PublishSubject<String> subject = PublishSubject.create();
		subject.subscribe(data -> System.out.println("Subscribe #1 => " +data));
		subject.onNext("1");
		subject.onNext("3");
		subject.subscribe(data -> System.out.println("Subscribe #2 => " + data));
		subject.onNext("5");
		subject.onComplete();
	}
	
	public void code2by28() {
		ReplaySubject<String> subject = ReplaySubject.create();
		subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
		subject.onNext("1");
		subject.onNext("3");
		subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
		subject.onNext("5");
		subject.onComplete();
	}
	
	public void code2by29() {
		String[] dt = {"1", "3", "5"};
		Observable<String> balls = Observable.interval(100L, TimeUnit.MILLISECONDS)
				.map(Long::intValue)
				.map(i -> dt[i])
				.take(dt.length);
		
		ConnectableObservable<String> source = balls.publish();
		source.subscribe(data -> System.out.println("Subscribe #1 => " + data));
		source.subscribe(data -> System.out.println("Subscribe #2 => " + data));
		source.connect();
		try {
			Thread.sleep(250);
			source.subscribe(data -> System.out.println("Subscribe #3 => " + data));
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		FirstExample demo = new FirstExample();
		demo.emit();
		demo.code2by2();
		demo.code2by3();
		demo.code2by7();
		demo.code2by10();
		demo.code2by14();
		demo.code2by16();
		demo.code2by19();
		demo.code2by20();
		demo.code2by23();
		demo.code2by24();
		demo.code2by26();
		demo.code2by27();
		demo.code2by28();
		demo.code2by29();
	}
}