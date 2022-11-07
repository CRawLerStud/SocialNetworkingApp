package repo.users;

import models.User;
import repo.InMemoryRepository;
import repo.RepositoryException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class UserFileRepository extends InMemoryRepository<Long, User> {
    private final String filename;

    public UserFileRepository(String filename) {
        this.filename = filename;
        loadData();
    }

    /**
     * Loads data from the filename
     */
    private void loadData() {
        Path path = Paths.get(filename);
        try{
            List<String> lines = Files.readAllLines(path);
            for(String line : lines){
                String[] words = line.split(";");
                User user = new User(words[1], words[2], LocalDate.parse(words[3]));
                user.setId(Long.parseLong(words[0]));
                super.save(user);
            }
        }
        catch(IOException | RepositoryException e) {
            System.err.println("Error while reading from file!");
            e.printStackTrace();
        }
    }

    @Override
    public void save(User entity) throws RepositoryException {
        super.save(entity);
        writeToFile();
    }

    @Override
    public User delete(Long aLong) throws RepositoryException {
        User deletedUser = super.delete(aLong);
        writeToFile();
        return deletedUser;
    }

    private void writeToFile(){
        try {
            FileWriter fw = new FileWriter(filename);
            fw.write("");
            for (User user : this.findAll()) {
                fw.append(user.toString()).append("\n");
            }
            fw.close();
        }
        catch(IOException e){
            System.err.println("Eroare la scrierea in fisier!");
            e.printStackTrace();
        }
    }
}
