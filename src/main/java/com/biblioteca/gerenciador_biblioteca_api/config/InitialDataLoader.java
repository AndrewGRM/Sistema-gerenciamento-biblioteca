package com.biblioteca.gerenciador_biblioteca_api.config;

import com.biblioteca.gerenciador_biblioteca_api.model.Book;
import com.biblioteca.gerenciador_biblioteca_api.model.Loan;
import com.biblioteca.gerenciador_biblioteca_api.model.Member;
import com.biblioteca.gerenciador_biblioteca_api.repository.BookRepository;
import com.biblioteca.gerenciador_biblioteca_api.repository.LoanRepository;
import com.biblioteca.gerenciador_biblioteca_api.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class InitialDataLoader implements CommandLineRunner {

    private BookRepository bookRepository;
    private MemberRepository memberRepository;
    private LoanRepository loanRepository;

    public InitialDataLoader(BookRepository bookRepository, MemberRepository memberRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Lógica para criar e salvar livros
        Book book1 = new Book(null, "O Senhor dos Anéis", "J.R.R. Tolkien", 1954, "Fantasia");
        bookRepository.save(book1);

        // Lógica para criar e salvar membros
        Member member1 = new Member(null, "Andrew", "andrew.grm@gmail.com", "940295212");
        memberRepository.save(member1);

        // Lógica para criar e salvar empréstimos
        Loan loan = new Loan(null, book1, member1, LocalDate.now(), null);
        loanRepository.save(loan);
    }
}
