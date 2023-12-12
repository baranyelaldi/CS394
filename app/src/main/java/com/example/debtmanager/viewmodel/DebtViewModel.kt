import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DebtViewModel : ViewModel() {
    private var _changeDebt = MutableLiveData<Int>(0)

    private var _debt = MutableLiveData<Int>(0)

    val changeDebt: LiveData<Int>
        get() = _changeDebt

    fun setChangeDebt(newDebt: Int) {
        _changeDebt.value = newDebt
    }

    val debt: LiveData<Int>
        get() = _debt

    fun setDebt(newDebt: Int) {
        _debt.value = newDebt
    }

    fun changeFriendDebt(friend: Friend, isBorrow: Boolean) {
        if (isBorrow) {
            _debt.value = _debt.value!! + _changeDebt.value!!
            _changeDebt.value = 5
        } else {
            _debt.value = _debt.value!! - _changeDebt.value!!
            _changeDebt.value = 6
        }
    }
}
