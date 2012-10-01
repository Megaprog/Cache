/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

import cache.KeyGenerator;
import cache.ObjectFactory;

import java.util.*;

/**
 * The factory which generates random Employees.
 * Also implements {@link KeyGenerator} for {@link TestObjectFactory}.
 */
public class RandomEmployeeFactory implements ObjectFactory<Integer, Employee>, KeyGenerator<Integer> {

    private static final String nameCharacters = "abcdefghijklmnopqrstuvwxyz";
    private static final int minNameLen = 3;
    private static final int maxNameLen = 10;

    protected Random random = new Random();
    protected Map<Integer, Employee> employees = new HashMap<>();
    protected List<Integer> keys = new ArrayList<>();
    protected int maxKeyValue;

    public RandomEmployeeFactory(int maxKeyValue, int employeesNumber) {
        this.maxKeyValue = maxKeyValue;

        for (int i = 1; i <= employeesNumber; i++) {
            Integer key = createRandomUniqueKey();
            Employee employee = createRandomEmployee(key);
            employees.put(key, employee);
            keys.add(key);
        }
    }

    @Override
    public Integer getKey() {
        int index = random.nextInt(keys.size());
        return keys.get(index);
    }

    @Override
    public Employee getObject(Integer key) {
        return employees.get(key);
    }

    protected Integer createRandomUniqueKey() {
        Integer key;
        do {
            key = random.nextInt(maxKeyValue) + 1;
        } while (employees.containsKey(key));
        return key;
    }

    protected Employee createRandomEmployee(Integer key) {
        String name = generateRandomName();
        return new Employee(key, name);
    }

    protected String generateRandomName() {
        int len = minNameLen + random.nextInt(maxNameLen - minNameLen + 1);
        StringBuilder sb = new StringBuilder(len);
        sb.append(Character.toUpperCase(generateRandomAlphaChar()));
        for (int i = 2; i <= len; i++) {
            sb.append(generateRandomAlphaChar());
        }
        return sb.toString();
    }

    protected char generateRandomAlphaChar() {
        return nameCharacters.charAt(random.nextInt(nameCharacters.length()));
    }

}
