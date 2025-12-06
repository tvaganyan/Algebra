package algebra.fields;

public interface Field {
    void o();
    void e();
    void sum(Field x, Field y);
    void dif(Field x, Field y);
    void mul(Field x, Field y);
    void div(Field x, Field y);
    void deg(Field x, int n);
    boolean isO();
    Field getNewO();
    Field getNewE();
    Field copy();
    boolean eq(Field f);
    FieldEnum getType();
}
