package cn.lxyhome.jetpackcamerax.fagment.esftest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FristViewModel : ViewModel() {
    val listData = MutableLiveData<Array<String>>().also {
        it.value =  arrayOf("123","fafdsf","sdfdsf","sfddsf","fdgfdg",
            "strhh","sgjsjg","gjdhj","stjsf","hsgfhgfs","ewtrg","23423","fsghgfs","fdgfdg","gfdgdg",
            "sdfhtrsh","fdgdnbrtj","afdg3wg","agabadfh","sdeahre","dfbfdah","3rqf","hjtrjha","ar3adfb",
            "sdfsahaeh","bzberh","dsgeaa","agdfbdaf")
    }
}