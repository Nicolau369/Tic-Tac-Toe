package com.exemple.tictactoe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

object GameData {
    private var _gameModel : MutableLiveData<GameModel> = MutableLiveData()
    var gameModel : LiveData<GameModel> = _gameModel
    var myID = ""


    fun saveGameModel(model: GameModel){
        _gameModel.postValue(model)
        if (model.gamaId!="-1"){
            Firebase.firestore.collection("games")
                .document(model.gameId)
                .set(model)
        }

    }

    fun fetchGameModel(){
        gameModel.value?.apply {
            if (gamaId!="-1"){
                Firebase.firestore.collection("games")
                    .document(gamaId)
                    .addSnapshotListener { value, error ->
                        val model = value?.toObject(GameModel::class.java)
                        _gameModel.postValue(model)
                    }
            }
        }
    }

}