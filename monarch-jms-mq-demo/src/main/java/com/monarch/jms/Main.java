package com.monarch.jms;

import com.monarch.jms.consumer.Consumer;
import com.monarch.jms.producer.Producer;

import javax.jms.JMSException;
import java.io.Console;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
/* */
//TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
// to see how IntelliJ IDEA suggests fixing it.
/* */
//TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
// for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
/* */
public class Main {

    public static void main(String[] args) {
        // Sanity check main() arguments and warn user
        if (args.length > 0) {
            System.out.println("\n!!!! WARNING: You have provided arguments to the Java main() function. JVM arguments " +
                    "(such as -Djavax.net.ssl.trustStore) must be passed before the main class or .jar you wish to run.\n\n");
            Console c = System.console();
            System.out.println("Press the Enter key to continue");
            c.readLine();
        }

        Producer producer = new Producer();
        Consumer consumer = new Consumer();

        try {
            producer.sendMessage();
            consumer.receiveMessage();
            recordSuccess();
        } catch (Exception e) {
            recordFailure(e);
        }
    }

    private static int status = 1;

    private static void recordSuccess() {
        System.out.println("SUCCESS");
        status = 0;
    }

    private static void recordFailure(Exception ex) {
        if (ex != null) {
            System.out.println(ex);
            if (ex instanceof JMSException) {
                processJMSException((JMSException) ex);
            }
        }
        System.out.println("FAILURE");
        status = -1;
    }

    private static void processJMSException(JMSException jmsex) {
        System.out.println(jmsex);
        Throwable innerException = jmsex.getLinkedException();
        if (innerException != null) {
            System.out.println("Inner exception(s):");
        }
        while (innerException != null) {
            System.out.println(innerException);
            innerException = innerException.getCause();
        }
    }
}
