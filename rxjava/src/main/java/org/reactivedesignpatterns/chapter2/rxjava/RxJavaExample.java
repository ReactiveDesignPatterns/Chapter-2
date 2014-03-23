package org.reactivedesignpatterns.chapter2.rxjava;

import rx.Observable;
import rx.functions.Action1;

public class RxJavaExample {
    public static void observe(String[] strings) {
        Observable.from(strings).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("Received " + s);
            }
        });
    }

    public static void main(String... args) {
        if (args.length == 0) {
            String[] strings = { "a", "b", "c" };
            observe(strings);
        } else
            observe(args);
    }
}
