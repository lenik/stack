package com.bee32.plover.arch.naming;

public class NamedNodeTestBase {

    TreeMapNode<String> foodMap;
    TreeMapNode<String> sportsMap;
    TreeMapNode<TreeMapNode<?>> lifeHub;

    TreeMapNode<String> colorMap;
    TreeMapNode<String> powerMap;
    TreeMapNode<TreeMapNode<?>> genHub;

    TreeMapNode<TreeMapNode<?>> root;

    public NamedNodeTestBase() {
        root = new TreeMapNode<TreeMapNode<?>>("root", TreeMapNode.class);
        lifeHub = new TreeMapNode<TreeMapNode<?>>("life-hub", root);
        genHub = new TreeMapNode<TreeMapNode<?>>("gen-hub", root);

        foodMap = new TreeMapNode<String>("food", String.class, lifeHub);
        sportsMap = new TreeMapNode<String>("sports", String.class, lifeHub);

        colorMap = new TreeMapNode<String>("color", String.class, genHub);
        powerMap = new TreeMapNode<String>("power", String.class, genHub);

        foodMap.addChild("apple", "Apple");
        foodMap.addChild("banana", "Banana");

        sportsMap.addChild("ping", "Ping-Pong");
        sportsMap.addChild("basket", "BasketBall");

        lifeHub.addChild("food", foodMap);
        lifeHub.addChild("sport", sportsMap);

        colorMap.addChild("red", "Red");
        colorMap.addChild("green", "Green");
        colorMap.addChild("blue", "Blue");

        powerMap.addChild("weak", "Weak");
        powerMap.addChild("strong", "Strong");

        genHub.addChild("color", colorMap);
        genHub.addChild("power", powerMap);

        root.addChild("life", lifeHub);
        root.addChild("gen", genHub);
    }

}
