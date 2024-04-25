import java.util.HashMap;
import java.util.Map;

public class Ass7b {
    public static void main(String[] args) {
        DocProxy proxy = new DocProxy();
        User user = new User("user1", "admin");

        proxy.uploadDoc("Document1.txt", "This is the content of Document1.", user);
        String content = proxy.downloadDoc("Document1.txt", user);
        System.out.println("Downloaded Content: " + content);

        proxy.editDoc("Document1.txt", "Updated content.", user);
        content = proxy.downloadDoc("Document1.txt", user);
        System.out.println("Updated Content: " + content);
    }

    static class DocProxy {
        private DocStorage storage = new DocStorage();
        private Map<String, User> sessions = new HashMap<>();

        public boolean uploadDoc(String name, String content, User user) {
            if (authenticate(user)) {
                log("Upload", user);
                return storage.upload(name, content);
            }
            return false;
        }

        public String downloadDoc(String name, User user) {
            if (authenticate(user)) {
                log("Download", user);
                return storage.download(name);
            }
            return "Access Denied";
        }

        public boolean editDoc(String name, String content, User user) {
            if (authenticate(user)) {
                log("Edit", user);
                return storage.edit(name, content);
            }
            return false;
        }

        private boolean authenticate(User user) {
            return user != null && "admin".equals(user.role);
        }

        private void log(String action, User user) {
            System.out.println("User " + user.id + " performed " + action);
        }
    }

    static class DocStorage {
        private Map<String, String> documents = new HashMap<>();

        public boolean upload(String name, String content) {
            documents.put(name, content);
            return true;
        }

        public String download(String name) {
            return documents.get(name);
        }

        public boolean edit(String name, String newContent) {
            if (documents.containsKey(name)) {
                documents.put(name, newContent);
                return true;
            }
            return false;
        }
    }

    static class User {
        String id;
        String role;

        public User(String id, String role) {
            this.id = id;
            this.role = role;
        }
    }
}
