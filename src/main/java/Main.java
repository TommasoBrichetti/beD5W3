import models.*;
import models.DAO.ItemsDAO;
import models.DAO.LoanDAO;
import models.DAO.UserDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class Main {

    //econdo sue istruzioni, le ricordo che dovrebbe andare tutto ma c'erano dei problemi irrisolti i quali ci
    // avevano lasciato molto dubbiosi sulla loro provenienza.

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


//          findByIsbn(10L);
//          deleteByIsbn(11L);

            setPrestito(13L, 5L);

//          getListaPrestati();

//            getByTitol("q");


    }
    // works
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

    // works
    public static void setMagazine(String title, Periodicity periodicity, String author, int pNUmber, int year){
        Magazines b = new Magazines();
        b.setTitle(title);
        b.setYear(year);
        b.setPageNumber(pNUmber);
        b.setPeriodicity(periodicity);

        ItemsDAO dao = new ItemsDAO();
        dao.save(b);
    }

    // works
    public static void setUser(String nome,  String cognome, String  bd){
        User b = new User();
        b.setCognome(cognome);
        b.setName(nome);
        b.setBirthDate(bd);

        UserDAO dao = new UserDAO();
        dao.save(b);
    }

    //works
    public static void findByIsbn(Long isbn){
        ItemsDAO dao = new ItemsDAO();
        System.out.println(dao.getById(isbn));
    }

    //need to be fixed
    public static void deleteByIsbn(Long isbn){
        ItemsDAO dao = new ItemsDAO();
        dao.delete(dao.getById(isbn));
        System.out.println("done");
    }

    public static void setPrestito(Long isbn, Long tessera){
        ItemsDAO idao = new ItemsDAO();
        Item i = idao.getById(isbn);
        UserDAO udao = new UserDAO();
        User u = udao.getById(tessera);

        if(i.getAvailability() == Availability.DISPONIBILE){

            i.setAvailability(Availability.PRESTATO);
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

    }

    public static void setRestituzione(Long isbn){
        ItemsDAO dao = new ItemsDAO();
        Item i = dao.getById(isbn);
        if (i.getAvailability() == Availability.DISPONIBILE){
            System.out.println("il libro è già in casa");
        }else{
            i.setAvailability(Availability.DISPONIBILE);
            List<Loan> loaned = i.getLoaned();
            for (int j = 0; j < loaned.size(); j++) {
               Loan o = loaned.get(j);
                LoanDAO ldao = new LoanDAO();
                ldao.delete(o);
            }
            System.out.println("done");
        }
        dao.refresh(i);
    }

    public static void getListaPrestati(Long tessera){
        UserDAO udao = new UserDAO();
        User u = udao.getById(tessera);
        List<Loan> loaned = u.getList();
        for (int j = 0; j < loaned.size(); j++) {
            System.out.println(loaned.get(j));
        }
        System.out.println("done");
    }

    public static void getPrestitiScaduti(){
        LoanDAO ldao = new LoanDAO();
        List <Loan> l = LoanDAO.getAllLoan();
        for (int i = 0; i < l.size(); i++) {
            if( l.get(i).getTerminePrestito().compareTo(LocalDate.now()) < 0){
                System.out.println(l.get(i) + " ha superato il termine di consegna ");
            }
        }
    }

    //not working
    public static void getByTitol(String q){
        ItemsDAO idao = new ItemsDAO();
        ItemsDAO.getByTitle(q);
    }

}
