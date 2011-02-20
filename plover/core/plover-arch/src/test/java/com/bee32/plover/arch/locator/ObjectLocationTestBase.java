package com.bee32.plover.arch.locator;

public class ObjectLocationTestBase {

    MapLocator<String> foodMap;
    MapLocator<String> sportsMap;
    MapLocator<MapLocator<?>> lifeHub;

    MapLocator<String> colorMap;
    MapLocator<String> powerMap;
    MapLocator<MapLocator<?>> genHub;

    MapLocator<MapLocator<?>> root;

    public ObjectLocationTestBase() {
        root = new MapLocator<MapLocator<?>>(MapLocator.class);
        lifeHub = new MapLocator<MapLocator<?>>(root);
        genHub = new MapLocator<MapLocator<?>>(root);

        foodMap = new MapLocator<String>(String.class, lifeHub);
        sportsMap = new MapLocator<String>(String.class, lifeHub);

        colorMap = new MapLocator<String>(String.class, genHub);
        powerMap = new MapLocator<String>(String.class, genHub);

        foodMap.setLocation("apple", "Apple");
        foodMap.setLocation("banana", "Banana");

        sportsMap.setLocation("ping", "Ping-Pong");
        sportsMap.setLocation("basket", "BasketBall");

        lifeHub.setLocation("food", foodMap);
        lifeHub.setLocation("sport", sportsMap);

        colorMap.setLocation("red", "Red");
        colorMap.setLocation("green", "Green");
        colorMap.setLocation("blue", "Blue");

        powerMap.setLocation("weak", "Weak");
        powerMap.setLocation("strong", "Strong");

        genHub.setLocation("color", colorMap);
        genHub.setLocation("power", powerMap);

        root.setLocation("life", lifeHub);
        root.setLocation("gen", genHub);
    }

}
