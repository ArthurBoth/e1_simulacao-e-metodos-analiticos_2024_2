package auxiliaries.queues;

public class DataWrapper {
    
    public double[] queueTimeStatus;    // status de quanto tempo haviam aquele numero de clientes na fila
    public int lossCount;               // quantos clientes não entraram na fila porque ela estava cheia
    public double endTime;              // tempo global ao fim da simulação

    public DataWrapper() {}
}
