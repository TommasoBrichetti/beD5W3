import models.*;
import models.DAO.ItemsDAO;
import models.DAO.LoanDAO;
import models.DAO.UserDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Main {

    //!  TUTTI GLI ERRORI SONO STATI CORRETTI AL MOMENTO TUTTI I METODI FUNZIONANO COME DA RICHIESTA

    public static void main(String[] args) {

        //        setBook("qqqq",Genres.FANTASY,"qqqq",2000,496);
        //        setBook("www",Genres.FANTASY,"wwww",2000,496);
        //        setBook("eeee",Genres.FANTASY,"eeee",2000,496);
        //        setBook("rrrr",Genres.FANTASY,"rrr",2000,496);
        //        setMagazine("vivo è donna",Periodicity.SEMESTER,"wow",2022,34);
        //        setUser("mario","rossi","22/02/2001");
        //        setUser("piero","angela","22/02/1936");
        //        setUser("dario","piedi","22/02/1999");
        //        setUser("linzey","susannina","22/02/2012");
        //        findByIsbn(10L);
        //        deleteByIsbn(16L);
        //        setPrestito(14L, 2L);
        //        setRestituzione(15L);
        //        getListaPrestati();
        //        getByAuthor("qq");
        //        getPrestitiScaduti();
        //        getListaPrestati(2L);
        setRestituzione(14L);

    }
    //*works
    public static void setBook(String title, Genres genre, String author, int year, int pNUmber){
        Book b = new Book();
        b.setTitle(title);
        b.setGenre(genre);
        b.setAuthor(author);
        b.setYear(year);
        b.setPageNumber(pNUmber);

        ItemsDAO dao = new ItemsDAO();
        dao.save(b);
    }

    //*works
    public static void setMagazine(String title, Periodicity periodicity, String author, int pNUmber, int year){
        Magazines b = new Magazines();
        b.setTitle(title);
        b.setYear(year);
        b.setPageNumber(pNUmber);
        b.setPeriodicity(periodicity);

        ItemsDAO dao = new ItemsDAO();
        dao.save(b);
    }

    //*works
    public static void setUser(String nome,  String cognome, String  bd){
        User b = new User();
        b.setCognome(cognome);
        b.setName(nome);
        b.setBirthDate(bd);

        UserDAO dao = new UserDAO();
        dao.save(b);
    }

    //*works
    public static void findByIsbn(Long isbn){
        ItemsDAO dao = new ItemsDAO();
        System.out.println(dao.getById(isbn));
    }

    //*works
    public static void deleteByIsbn(Long isbn){
        ItemsDAO dao = new ItemsDAO();
        dao.delete(dao.getById(isbn));
        System.out.println("done");
    }

    //*works
    public static void setPrestito(Long isbn, Long tessera){
        ItemsDAO idao = new ItemsDAO();
        Item i = idao.getById(isbn);
        UserDAO udao = new UserDAO();
        User u = udao.getById(tessera);

        if(i != null && u != null){
            if(i.getAvailability() == Availability.DISPONIBILE){

                idao.toggle(isbn);

                Loan l = new Loan();
                l.setUser(u);
                l.setItem(i);

                LoanDAO ldao = new LoanDAO();
                ldao.save(l);
                System.out.println("done");

            }else{
                System.out.println("il libro non è disponibile");
            }
        }else{
            System.out.println("utente o libro non trovato");
        }
    }

    //*works
    public static void setRestituzione(Long isbn){
        ItemsDAO dao = new ItemsDAO();
        Item i = dao.getById(isbn);
        if (i.getAvailability() == Availability.DISPONIBILE){
            System.out.println("il libro è già in casa");
        }else{
            dao.toggle(isbn);
            List <Loan> l = LoanDAO.getAllLoan();
            for (int j = 0; j < l.size(); j++){
                if (Objects.equals(l.get(j).getItem().getIsbn(), isbn)){
                   LoanDAO.delete(l.get(j));
                }
            }
            System.out.println("done");
        }
    }

    //*works
    public static void getListaPrestati(Long tessera){
        List <Loan> l = LoanDAO.getAllLoan();
        for (int i = 0; i < l.size(); i++){
            if (Objects.equals(l.get(i).getUser().getNumeroTessera(), tessera)){
                System.out.println(l.get(i));
            }
        }
//?        size non va il metodo sopra è un sostituto funzionante, era possibile fare anche un altra querie!
//        List<Loan> loaned = u.getList();
//        System.out.println(loaned.get(0));
//        for (int j = 0; j < loaned.size(); j++) {
//            System.out.println(loaned.get(j));
//        }
        System.out.println("done");
    }

    //*works
    public static void getPrestitiScaduti(){
        int n = 0;
        LoanDAO ldao = new LoanDAO();
        List <Loan> l = LoanDAO.getAllLoan();
        for (int i = 0; i < l.size(); i++) {
            if( l.get(i).getTerminePrestito().compareTo(LocalDate.now()) < 0){
                System.out.println("oltre data limite");
                System.out.println(l.get(i).getUser()+" "+l.get(i).getItem() +" "+ l.get(i).getTerminePrestito());
                n++;
            }
        }
        if (n == 0) {
            System.out.println("nessun prestito scaduto trovato");
        }
    }

    //*works
    public static void getByTitol(String q){
        for (int i = 0; i < ItemsDAO.getItemByTile(q).size(); i++) {
            System.out.println(ItemsDAO.getItemByTile(q).get(i));
        }
    }

    //*works
    public static void getByAuthor(String q){
        for (int i = 0; i < ItemsDAO.getItemByAuthor(q).size(); i++) {
            System.out.println(ItemsDAO.getItemByAuthor(q).get(i));
        }
    }

}
