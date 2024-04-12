package tests.L5_6.Utils;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import tests.L5_6.models.MapField;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {

    public static <K, V> Map<K, V> zipToMap(List<K> keys, List<V> values) {
        return IntStream.range(0, keys.size()).boxed()
                .collect(Collectors.toMap(keys::get, values::get));
    }

    public static <T>List<T> toList(ElementsCollection collection, List<String> headerTexts, Class<T> tClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Object> resultList = new ArrayList<>();
        for (SelenideElement row : collection) {
            T task = tClass.getConstructor().newInstance();
            for (int i = 0; i < headerTexts.size(); i++) {

                SelenideElement cell = row.$$("[role=cell]").get(i); // weak

                String cellText = cell.text();
                String header = headerTexts.get(i);
                Field[] declaredFields = task.getClass().getDeclaredFields();

                for (Field field : declaredFields){
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(MapField.class)){
                        MapField annotation = field.getAnnotation(MapField.class);
                        if (annotation.columnName().equals(header)){
                            if (field.getType().equals(Double.class)){
                                Double value = Double.parseDouble(
                                        cellText.replaceAll("%", "")
                                                .split(" ")[0]);
                                try {
                                    field.set(task, value);
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                }

                            } else {
                                try {
                                    field.set(task, cellText);
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        }

                    }
                }
            }
            resultList.add(task);
        }
        return (List<T>) resultList;
    }
}
