package ir.sahabino.rules_evaluator.dao;

import ir.sahabino.rules_evaluator.conf.Conf;

public class KafKaCandleConsumer {
    private Conf config;

    private KafKaCandleConsumer(Conf config) {
        this.config = config;
    }

    public static KafKaCandleConsumer of(Conf config) {
        return new KafKaCandleConsumer(config);
    }
}
