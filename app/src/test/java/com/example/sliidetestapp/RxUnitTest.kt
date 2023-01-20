package com.example.sliidetestapp

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.AfterClass
import org.junit.BeforeClass
import java.util.concurrent.atomic.AtomicInteger

abstract class RxUnitTest {


    companion object {

        @JvmStatic
        @BeforeClass
        fun classSetup() {
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setSingleSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        }

        @JvmStatic
        @AfterClass
        fun classTeardown() {
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.io() }
            RxJavaPlugins.setComputationSchedulerHandler { Schedulers.computation() }
            RxJavaPlugins.setSingleSchedulerHandler { Schedulers.single() }
            RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.newThread() }
        }

        @JvmStatic
        fun createCompletableWithTestObserver(testObserver: TestObserver<*>) : Completable {
            return Completable.never()
                .doOnSubscribe(doOnSubscribe(testObserver))
        }

        @JvmStatic
        private fun doOnSubscribe(testObserver: TestObserver<*>): Consumer<Disposable> {
            return Consumer { disposable: Disposable ->
                if (!testObserver.hasSubscription()) {
                    testObserver.onSubscribe(disposable)
                }
            }
        }
    }
}