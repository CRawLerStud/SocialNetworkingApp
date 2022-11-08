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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            System.err.println("Error while writing in the users' file!");
            e.printStackTrace();
        }
    }

    public List<User> lastnameIs(String lastname){
        List<User> result = new ArrayList<>();
        for(User user : this.findAll()){
            if(user.getLastname().equals(lastname))
                result.add(user);
        }
        return result;
    }

    public List<User> surnameIs(String surname){
        List<User> result = new ArrayList<>();
        for(User user : this.findAll()){
            if(user.getSurname().equals(surname))
                result.add(user);
        }
        return result;
    }

    public List<User> fullnameIs(String lastname, String surname){
        List<User> lastnameUsers = this.lastnameIs(lastname);
        List<User> surnameUsers = this.surnameIs(surname);
        return lastnameUsers.stream()
                .distinct()
                .filter(surnameUsers::contains)
                .collect(Collectors.toList());
    }

    public List<User> lastnameStartsWith(String sequence){
        List<User> result = new ArrayList<>();
        for(User user : this.findAll())
            if(user.getLastname().startsWith(sequence))
                result.add(user);
        return result;
    }

    public List<User> surnameStartsWith(String sequence){
        List<User> result = new ArrayList<>();
        for(User user : this.findAll())
            if(user.getSurname().startsWith(sequence))
                result.add(user);
        return result;
    }

    public List<User> usersOlderThan(int minimumAge){
        List<User> result = new ArrayList<>();
        for(User user : this.findAll())
            if(user.getYears() >= minimumAge)
                result.add(user);
        return result;
    }

    public User changeUserLastname(Long ID, String newLastname) throws RepositoryException{
        User foundUser = this.findOne(ID);
        foundUser.setLastname(newLastname);
        writeToFile();
        return foundUser;
    }

    public User changeUserSurname(Long ID, String newSurname) throws RepositoryException{
        User foundUser = this.findOne(ID);
        foundUser.setSurname(newSurname);
        writeToFile();
        return foundUser;
    }

    public User changeUserBirthday(Long ID, LocalDate newBirthDate) throws RepositoryException{
        User foundUser = this.findOne(ID);
        foundUser.setBirthDate(newBirthDate);
        writeToFile();
        return foundUser;
    }
}
