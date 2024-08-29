package auxiliaries;

public abstract class Configs {

    // Confgurações de simulação
    public static final int ITERATIONS  = 10;

    // Configurações do gerador de números pseudoaleatórios
    public static final double SEED         = 3.1415926535;     
    public static final int MULTIPLIER      = 157907;            
    public static final double INCREMENT    = 14423.2563217511;  
    public static final double MODULE       = 16651.8647227172;  

    // Congifurações da fila
    public static final int QUEUE_CAPACITY          = 5;
    public static final int QUEUE_SERVERS           = 1;

    // configurações de Evento
    public static final double MIN_TIME_TO_ARRIVE   = 2;
    public static final double MAX_TIME_TO_ARRIVE   = 5;
    public static final double MIN_TIME_TO_LEAVE    = 3;
    public static final double MAX_TIME_TO_LEAVE    = 5;

    private Configs() {}
}
