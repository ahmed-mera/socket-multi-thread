package Bean;

public class Offer {
    private RegistrationClient client;
    private RegistrationObject object;


    public Offer(RegistrationClient client, RegistrationObject object) {
        this.client = client;
        this.object = object;
    }

    public RegistrationClient getClient() {
        return client;
    }

    public void setClient(RegistrationClient client) {
        this.client = client;
    }

    public RegistrationObject getObject() {
        return object;
    }

    public void setObject(RegistrationObject object) {
        this.object = object;
    }


    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "\n\t\t\tha vinto l'asta il " + client.toString() + "," + object.toString();
    }
}
