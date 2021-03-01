package com.shcheglov.task.consoleroulette.service;

import com.shcheglov.task.consoleroulette.converter.InputToBetConverter;
import com.shcheglov.task.consoleroulette.dao.BetDao;
import com.shcheglov.task.consoleroulette.dao.PlayerDao;
import com.shcheglov.task.consoleroulette.domain.Bet;
import com.shcheglov.task.consoleroulette.validator.BetAmountValidator;
import com.shcheglov.task.consoleroulette.validator.BetTypeValidator;
import com.shcheglov.task.consoleroulette.validator.GeneralInputValidator;
import com.shcheglov.task.consoleroulette.validator.NameValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service("betService")
@Slf4j
public class BetServiceImpl implements BetService {

    private final GeneralInputValidator generalInputValidator;
    private final NameValidator nameValidator;
    private final BetTypeValidator betTypeValidator;
    private final BetAmountValidator betAmountValidator;
    private final InputToBetConverter inputToBetConverter;
    private final PlayerDao playerDao;
    private final BetDao betDao;

    @Autowired
    public BetServiceImpl(@Qualifier("generalInputValidator") final GeneralInputValidator generalInputValidator,
                          @Qualifier("nameValidator") final NameValidator nameValidator,
                          @Qualifier("betTypeValidator") final BetTypeValidator betTypeValidator,
                          @Qualifier("betAmountValidator") final BetAmountValidator betAmountValidator,
                          @Qualifier("inputToBetConverter") final InputToBetConverter inputToBetConverter,
                          @Qualifier("playerDao") final PlayerDao playerDao,
                          @Qualifier("betDao") final BetDao betDao) {
        this.generalInputValidator = generalInputValidator;
        this.nameValidator = nameValidator;
        this.betTypeValidator = betTypeValidator;
        this.betAmountValidator = betAmountValidator;
        this.inputToBetConverter = inputToBetConverter;
        this.playerDao = playerDao;
        this.betDao = betDao;
    }

    @Override
    public Bet createBet(final String betRawInput) {
        log.debug(String.format("Creating a new bet based on the raw input: [%s]", betRawInput));
        return Stream.ofNullable(betRawInput)
                .map(rawInput -> rawInput.split(" "))
                .peek(generalInputValidator)
                .peek(nameValidator)
                .peek(betTypeValidator)
                .peek(betAmountValidator)
                .map(rawInputTokens -> playerDao.getByName(rawInputTokens[0])
                        .map(player -> inputToBetConverter.convert(player, rawInputTokens))
                        .orElseThrow(() -> new IllegalArgumentException(String.format("No such player found [%s]!", rawInputTokens[0]))))
                .map(betDao::save)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No input provided!"));
    }

}
