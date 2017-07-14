package me.lemuel.adore.mvp.presenter;

import me.lemuel.adore.mvp.Depositary;
import me.lemuel.adore.mvp.LocalDepositary;

public abstract class AdoreBasePresenter {
    protected Depositary mDepositary = Depositary.getInstance();
    protected LocalDepositary mLocalDepositary = LocalDepositary.getInstance();
}
