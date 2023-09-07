package gr.aueb.cf.springteacher4.service.exceptions;

public class EntityNotFoundException extends Exception {
    private static final long serialVersionUID = 123456789L;

    public EntityNotFoundException(Class<?> entityClass, Long id) {
        super("Entity " + entityClass.getSimpleName() + " with id: " + id + " does not exist");
    }
}
