import java.util.UUID;

public class Ass7a {
    public static void main(String[] args) {
        Handler handler = new HwHandler(null);
        handler = new SwHandler(handler);
        handler = new NwHandler(handler);

        Request hwRequest = new Request("Hardware", "server is ", Priority.HIGH);
        Request swRequest = new Request("Software", "database returns an error on query.", Priority.MEDIUM);
        Request nwRequest = new Request("Network", "Cannot connect to the internet.", Priority.LOW);

        handler.handle(hwRequest);
        handler.handle(swRequest);
        handler.handle(nwRequest);
    }

    enum Priority {
        LOW, MEDIUM, HIGH
    }

    static class Request {
        String id;
        String title;
        String description;
        Priority priority;

        Request(String title, String description, Priority priority) {
            this.id = UUID.randomUUID().toString();
            this.title = title;
            this.description = description;
            this.priority = priority;
        }
    }

    interface Handler {
        void handle(Request request);
    }

    static abstract class BaseHandler implements Handler {
        protected Handler next;

        public BaseHandler(Handler next) {
            this.next = next;
        }

        protected void pass(Request request) {
            if (next != null) {
                next.handle(request);
            } else {
                System.out.println("No handler available for " + request.title + " with ID " + request.id);
            }
        }
    }

    static class HwHandler extends BaseHandler {
        public HwHandler(Handler next) {
            super(next);
        }

        public void handle(Request request) {
            if (request.title.contains("Hardware")) {
                System.out.println("Handling hardware support for: " + request.description + " with ID " + request.id);
            } else {
                pass(request);
            }
        }
    }

    static class SwHandler extends BaseHandler {
        public SwHandler(Handler next) {
            super(next);
        }

        public void handle(Request request) {
            if (request.title.contains("Software")) {
                System.out.println("Handling software support for: " + request.description + " with ID " + request.id);
            } else {
                pass(request);
            }
        }
    }

    static class NwHandler extends BaseHandler {
        public NwHandler(Handler next) {
            super(next);
        }

        public void handle(Request request) {
            if (request.title.contains("Network")) {
                System.out.println("Handling network support for: " + request.description + " with ID " + request.id);
            } else {
                pass(request);
            }
        }
    }
}
