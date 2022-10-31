using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GameManager : MonoBehaviour
{
    public Text txtPlayerScore;
    public Text txtComputerScore;

    public int playerScore;
    public int computerScore;

    public void IncrementScore(string colliderName)
    {
        AIController ai = new AIController();
        switch (colliderName)
        {
            case "Bounds South":
                computerScore++;
                txtComputerScore.text = "Computer: " + computerScore;
                return;
            case "Bounds North":
                playerScore++;
                txtPlayerScore.text = "Player: " + playerScore;
                ai.skill += .001f;
                return;
        }
    }
}
