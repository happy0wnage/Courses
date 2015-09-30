package ua.nure.petrov.Course.db.parser.security;


public class URI {

    private final String uri;

    public URI(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        URI uri1 = (URI) o;

        return !(uri != null ? !uri.equals(uri1.uri) : uri1.uri != null);

    }

    @Override
    public int hashCode() {
        return uri != null ? uri.hashCode() : 0;
    }

}
