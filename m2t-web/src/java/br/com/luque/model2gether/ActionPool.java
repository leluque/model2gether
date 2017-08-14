package br.com.luque.model2gether;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author leand
 */
public class ActionPool {

    private final Map<Model, ModelActionPool> modelsActionPool = new HashMap();

    private final static ActionPool singleton = new ActionPool();

    private ActionPool() {
        if (singleton != null) {
            throw new IllegalStateException("Only one object of ActionPool may exist.");
        }
        this.add(new Model(1L));
    }

    public static ActionPool getInstance() {
        return singleton;
    }

    public void add(Model model) {
        this.add(model, new ModelActionPool());
    }

    public void add(Model model, ModelActionPool modelActionPool) {
        this.modelsActionPool.put(model, modelActionPool);
    }

    public ModelActionPool getFor(Model model) {
        return this.modelsActionPool.get(model);
    }

    public ModelActionPool getFor(String modelId) {
        return this.modelsActionPool.get(new Model(Long.valueOf(modelId)));
    }

}
