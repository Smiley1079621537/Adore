package me.lemuel.adore.presenter;

import me.lemuel.adore.Depositary;
import me.lemuel.adore.LocalDepositary;

public abstract class AdoreBasePresenter {
    protected Depositary mDepositary = Depositary.getInstance();
    protected LocalDepositary mLocalDepositary = LocalDepositary.getInstance();
}
