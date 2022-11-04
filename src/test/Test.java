package test;

public class Test {

    public static void executeAll(){
        TestModelsLayer.executeAll();
        TestRepoLayer.executeAll();
        TestValidatorLayer.executeAll();
        TestServiceLayer.executeAll();
    }
}
