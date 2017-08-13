package java8.ch5stream;

import java8.factory.Trader;
import java8.factory.Transaction;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Usage: <b> Practice: transaction and trader </b>
 *
 * @author lucifer
 *         Date 2017-5-31
 *         Device Aurora R5
 */
public class TransactionPractice {
    public static void main(String[] args) {
        List<Transaction> transactions = buildTransactions();

        // 1. Find all trans in year 2011, and order by value asc
        transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .forEach(System.out::println);
        System.out.println();

        // 2. Find city set
        transactions.stream().map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .forEach(System.out::println);
        System.out.println();

        // 3. Find all traders in Cambridge and order by name
        transactions.stream().map(Transaction::getTrader)
                .filter(trader -> "Cambridge".equalsIgnoreCase(trader.getCity()))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .forEach(System.out::println);
        System.out.println();

        // 4. Find names of traders and sort by alphabet
        System.out.println(transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .distinct()
                .sorted()
                .collect(Collectors.joining(" ")));
        System.out.println();

        // 5. Any trader work in Milan?
        Optional<Trader> traderOptional = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> "Milan".equalsIgnoreCase(trader.getCity()))
                .findAny();
        traderOptional.ifPresent(System.out::println);
        System.out.println();

        // 6. Sum values of traders in Cambridge
        long sum = transactions.stream()
                .filter(transaction -> "Cambridge".equalsIgnoreCase(transaction.getTrader().getCity()))
                .map(Transaction::getValue)
                .reduce(0, Integer::sum);
        System.out.println(sum);
        System.out.println();

        // 7. Find highest value
        OptionalInt highest = transactions.stream()
                .mapToInt(Transaction::getValue)
                .max();
        highest.ifPresent(System.out::println);
        System.out.println();

        // 8. Find transaction with lowest value
        Optional<Transaction> trans = transactions.stream()
                .min(Comparator.comparing(Transaction::getValue));
        trans.ifPresent(System.out::println);
    }

    private static List<Transaction> buildTransactions() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        return Arrays.asList(
                new Transaction(1, brian, 2011, 300),
                new Transaction(2, raoul, 2012, 1000),
                new Transaction(3, raoul, 2011, 400),
                new Transaction(4, mario, 2012, 710),
                new Transaction(5, mario, 2012, 700),
                new Transaction(6, alan, 2012, 950)
        );
    }
}
