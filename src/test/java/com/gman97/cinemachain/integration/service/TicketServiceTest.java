package com.gman97.cinemachain.integration.service;

import com.gman97.cinemachain.dto.TicketCreateDto;
import com.gman97.cinemachain.entity.SeatsForFilmSession;
import com.gman97.cinemachain.entity.Status;
import com.gman97.cinemachain.integration.IntegrationTestBase;
import com.gman97.cinemachain.repository.SeatsRepository;
import com.gman97.cinemachain.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.hibernate.StaleObjectStateException;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@RequiredArgsConstructor
class TicketServiceTest extends IntegrationTestBase {

    private final TicketService ticketService;
    private final SeatsRepository seatsRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Test
    @Transactional
    void bayTickets() {
        var seat = seatsRepository.findAllByFilmSessionId(2L).get(0);
        var dto = TicketCreateDto.builder()
                .title("Mgla")
                .beginSession(LocalTime.of(12, 50))
                .date(LocalDate.now().plusDays(1))
                .duration("126")
                .filmSessionId(2L)
                .cinemaName("Mir")
                .hallNumber(2)
                .row(seat.getRow())
                .seatNo(seat.getSeatNo())
                .seatId(seat.getId())
                .cost(seat.getTicketCost().doubleValue())
                .build();

        var actual = ticketService.bayTickets(List.of(dto));

        assertThat(actual).hasSize(1);
        assertThat(seat.getStatus()).isEqualTo(Status.FREE);
        assertThat(seatsRepository.findById(seat.getId()).get().getStatus()).isEqualTo(Status.TAKEN);
    }

    @Test

    @Sql({"classpath:sql/data.sql"})
    @Sql(value = {"classpath:sql/clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void bayTicketsShouldThrowExceptionAndRollback_whenAnotherTransactionAlreadyModifiedRowsInDB() {
        var transaction = platformTransactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));

        List<SeatsForFilmSession> seats = seatsRepository.findAllByFilmSessionId(2L);

        var seat1 = seats.get(0);
        var seat2 = seats.get(1);

        // Меняем статус для первого места (имитируем, что оно было выкуплено другой транзакцией)
        seat1.setStatus(Status.TAKEN);
        seatsRepository.saveAndFlush(seat1);
        platformTransactionManager.commit(transaction);

        var dto1 = TicketCreateDto.builder()
                .title("Mgla")
                .beginSession(LocalTime.of(12, 50))
                .date(LocalDate.now().plusDays(1))
                .duration("126")
                .filmSessionId(2L)
                .cinemaName("Mir")
                .hallNumber(2)
                .row(seat1.getRow())
                .seatNo(seat1.getSeatNo())
                .seatId(seat1.getId())
                .cost(seat1.getTicketCost().doubleValue())
                .build();

        var dto2 = TicketCreateDto.builder()
                .title("Mgla")
                .beginSession(LocalTime.of(12, 50))
                .date(LocalDate.now().plusDays(1))
                .duration("126")
                .filmSessionId(2L)
                .cinemaName("Mir")
                .hallNumber(2)
                .row(seat2.getRow())
                .seatNo(seat2.getSeatNo())
                .seatId(seat2.getId())
                .cost(seat2.getTicketCost().doubleValue())
                .build();

        assertThrows(StaleObjectStateException.class, () -> ticketService.bayTickets(List.of(dto1, dto2)));

        transaction = platformTransactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));

        assertThat(seatsRepository.findById(seat1.getId()).get().getStatus()).isEqualTo(Status.TAKEN);
        //Если второе место свободно, значит, транзакция откатилась
        assertThat(seatsRepository.findById(seat2.getId()).get().getStatus()).isEqualTo(Status.FREE);

        platformTransactionManager.rollback(transaction);
    }
}