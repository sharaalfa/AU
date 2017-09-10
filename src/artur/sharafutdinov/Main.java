package artur.sharafutdinov;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        final String DELIMITER_INPUT_DATA = " ";

        String fileName = "../example.txt";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            List<Order> listSale = new ArrayList<>();
            List<Order> listBuy = new ArrayList<>();
            List<AggregateOrder> listSaleAggregate = new ArrayList<>();
            List<AggregateOrder> listBuyAggregate = new ArrayList<>();
            int spaceOne, spaceTwo;
            String type = "";
            int volume = 0;
            double price = 0;
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    spaceOne = line.indexOf(DELIMITER_INPUT_DATA, 0);
                    spaceTwo = line.indexOf(DELIMITER_INPUT_DATA, spaceOne + 1);
                    type = line.substring(0, spaceOne);
                    volume = Integer.parseInt(line.substring(spaceOne + 1, spaceTwo));
                    price = Double.parseDouble(line.substring(spaceTwo + 1));
                    if (type.equals("B"))
                        listBuy.add(new Order(type, volume, price));
                    else if (type.equals("S"))
                        listSale.add(new Order(type, volume, price));
                } catch (StringIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }

            try {
                Collections.sort(listBuy);
                Collections.sort(listSale);
                int s = 0;
                if (0 < listSale.size() && listSale.size() < 1000000) {

                    int vlm = listSale.get(0).getVolume();
                    listSaleAggregate.add(new AggregateOrder(vlm, listSale.get(0).getPrice()));
                    for (int i = 1; i < listSale.size(); i++) {
                        if (listSale.get(i - 1).getPrice() == listSale.get(i).getPrice()) {
                            vlm += listSale.get(i).getVolume();
                            listSaleAggregate.set(s, new AggregateOrder(vlm, listSale.get(i).getPrice()));
                        }
                        else {
                            vlm = listSale.get(i).getVolume();
                            s++;
                            listSaleAggregate.add(new AggregateOrder(vlm, listSale.get(i).getPrice()));
                        }
                    }

                }
                int b = 0;
                if (0 < listBuy.size() && listBuy.size() < 1000000) {

                    int vlm = listBuy.get(0).getVolume();
                    listBuyAggregate.add(new AggregateOrder(vlm, listBuy.get(0).getPrice()));

                    for (int i = 1; i < listBuy.size(); i++) {

                        if (listBuy.get(i - 1).getPrice() == listBuy.get(i).getPrice()) {

                            vlm += listBuy.get(i).getVolume();
                            listBuyAggregate.set(b, new AggregateOrder(vlm, listBuy.get(i).getPrice()));



                        }
                        else {
                            vlm = listBuy.get(i).getVolume();
                            b++;
                            listBuyAggregate.add(new AggregateOrder(vlm, listBuy.get(i).getPrice()));
                        }
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            Map<Double, Integer> map = new HashMap<>();
            for (int i = 0; i < listBuyAggregate.size(); i++) {
                for (int j = 0; j < listSaleAggregate.size(); j++) {
                    if(listBuyAggregate.get(i).getPrice() == listSaleAggregate.get(j).getPrice()) {
                        if(listBuyAggregate.get(i).getVolume() == listSaleAggregate.get(j).getVolume()) {
                            map.put(listBuyAggregate.get(i).getPrice(), listBuyAggregate.get(i).getVolume());
                        }
                        else if(listBuyAggregate.get(i).getVolume() < listSaleAggregate.get(j).getVolume()) {
                            for (int k = 0; k < listBuyAggregate.size(); k++) {
                                if (listBuyAggregate.get(k).getPrice() > listSaleAggregate.get(j).getPrice())
                                    map.put(listSaleAggregate.get(j).getPrice(),listSaleAggregate.get(j).getVolume());
                            }
                        }
                        else {
                            for (int k = 0; k < listSaleAggregate.size(); k++) {
                                if (listSaleAggregate.get(k).getPrice() < listBuyAggregate.get(i).getPrice())
                                    map.put(listBuyAggregate.get(i).getPrice(),listBuyAggregate.get(i).getVolume());
                            }
                        }
                    }
                    else if (listBuyAggregate.get(i).getPrice() > listSaleAggregate.get(j).getPrice()) {
                        if(listBuyAggregate.get(i).getVolume() < listSaleAggregate.get(j).getVolume())
                            map.put(listSaleAggregate.get(j).getPrice(), listBuyAggregate.get(i).getVolume());
                        else if(listBuyAggregate.get(i).getVolume() > listSaleAggregate.get(j).getVolume())
                            map.put(listSaleAggregate.get(j).getPrice(), listSaleAggregate.get(j).getVolume());
                        else if(listBuyAggregate.get(i).getVolume() == listSaleAggregate.get(j).getVolume())
                            map.put(listSaleAggregate.get(j).getPrice(), listSaleAggregate.get(j).getVolume());

                    }
                    else {
                        map.put(0.0,0);
                    }
                }
            }


            Map<Double, Integer> map2 = new HashMap<>();
            for (Map.Entry entry: map.entrySet()) {
                double score = 0.0;
                int count = 0;
                Object value = entry.getValue();
                for (Map.Entry entry1 : map.entrySet()) {
                    Object key2 = entry1.getKey();
                    Object value2 = entry1.getValue();
                    if (value.equals(value2)) {
                        score += (Double) key2;
                        count++;
                    }
                }
                score = score/count;
                map2.put(score, (Integer) value);
            }
            if(!map2.entrySet().stream().max(Map.Entry.comparingByValue(Integer::compareTo))
                    .get().getValue().equals(0)) {
                System.out.println("OUTPUT:" + "\n"
                        + map2.entrySet().stream().max(Map.Entry.
                        comparingByValue(Integer::compareTo)).get().getValue() +
                        " " + map2.entrySet().stream()
                        .max(Map.Entry.comparingByValue(Integer::compareTo)).get().getKey());
            } else
                System.out.println("OUTPUT:" + "\n" + 0 + " n/a");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
