//Randyll Bearer - rlb97@pitt.edu   Project 1.A: Blackjack

#include<stdio.h>
#include<stdlib.h>
#include<time.h>

int getValue();	//Generate a random Card [ace, 2-10, j,q,k], return its numeric blackjack value [1-11]

main(){
	srand(time(NULL));		//Declare/Seed srand
	int dealerHand[2] = {0};	//Card1 + Existing Total +  New Card
	int playerHand[2] = {0};	//Existing Total + New Card
	int dealerHandSize = 0;		//How many cards player has in hand
	int playerHandSize = 0;	
	int dealerAce = 0;		//Does the dealer have an ace in play for 11? 1 = true
	int playerAce = 0;
	int dealerTotal = 0;
	int playerTotal = 0;
	int playerState = 0;		//0 - still playing, 1 - chose to stand
	int dealerState = 0;

	int i;
	for(i = 0; i < 2; i++){	//Initialize starting hands [2 times]
		//Dealer
		int cardToAdd = getValue();	//Generates a card and returns its numeric blackjack value
		if(cardToAdd == 11){	//If we drew an ace
			if(dealerAce == 1){	//if we already have an ace in play for 11, play this one for 1
				cardToAdd = 1;	
			}else{
				dealerAce = 1;	//Dealer has an ace in play for 11 already, so must play this one for 1
			}
		}
		dealerHand[i] = cardToAdd;	//Add this newly generated card to player hand
		//Player
		cardToAdd = getValue();
		if(cardToAdd == 11){
			if(playerAce == 1){
				cardToAdd = 1;
			}else{
				playerAce = 1;
			}
		}
		playerHand[i] = cardToAdd;
		//Prepare for while loops, finalize array formats
		if(i == 1){
			dealerTotal = dealerHand[0] + dealerHand[1];
			playerTotal = playerHand[0] + playerHand[1];
		}	
	}
	//Player Turn
	while(playerState == 0){
		printf("\nThe Dealer:\n");
		if(dealerTotal == 21){	//Player must tie, dealer has natural blackjack
			printf("%d + %d = 21, You MUST tie the Dealer to not lose!\n", dealerHand[0], dealerHand[1]);
			dealerState = 1;
		}else{			//Dealer may still play
			printf("? + %d \n", dealerHand[1]);
		}
		printf("\nYou:\n");
		if(playerTotal > 21){	//If > 21, player busted -> game over
			printf("%d + %d = %d YOU BUSTED!\n", playerHand[0], playerHand[1], playerHand[0] + playerHand[1]);
			printf("\nDealer had %d + %d = %d", dealerHand[0], dealerHand[1], dealerHand[0] + dealerHand[1]);
			printf("\nBetter luck next time!\n\n");
			return 0;	//Game over
		}else{	//Player must choose to either 'Hit' or 'Stand'
			printf("%d + %d = %d\n", playerHand[0], playerHand[1], playerHand[0] + playerHand[1]);
			printf("\nWould you like to 'Hit' or 'Stand'?    ");				
			char userString[6];
			scanf("%s", userString);	//Read in Player Choice
			if(strcmp(userString, "stand") == 0 || strcmp(userString, "STAND") == 0){	//Player will pass turn to the dealer
				playerState = 1;
			}else if(strcmp(userString, "hit") == 0 || strcmp(userString, "HIT") == 0){	//Player will be dealt another card
				int cardToAdd = getValue();	//Get another value from a randomly generated card
				if(cardToAdd == 11){	//Need to check for multiple aces again
					if(playerAce == 1){
						cardToAdd = 1;	//If player already has an ace, they cannot play 2 for 11
					}else{
						playerAce = 1;	//Player now has an ace in play for 11
					}
				}
				playerHand[0] = playerHand[0] + playerHand[1]; //Shift it all leftmost
				playerHand[1] = cardToAdd;
				playerTotal = playerHand[0] + playerHand[1];	//Update total with new card
				if(playerTotal > 21 && playerAce == 1){		//If the player is at risk of busting and has an ace in play for 11
					if(playerHand[1] == 11){	//The most recent card was the ace
						playerHand[1] = 1;
					}else{				//The ace is hidden in the total
						playerHand[0] = playerHand[0] - 10;
						printf("Player forced to convert an ace from 11 to 1\n");
					}
					playerAce = 0;	//We converted the ace we had
					playerTotal = playerHand[0] + playerHand[1];	//Update total to reflect change
				}
			}else{	//ERROR
				printf("ERROR: INVALID INPUT\n\n");
				return 1;
			}
		}
	}			
	//Dealer Turn		
	while(dealerState == 0){
		printf("\nYou:\n");
		if(playerTotal == 21){
			printf("%d + %d = 21, You win if the Dealer cannot tie!\n", playerHand[0], playerHand[1]);
		}else{
			printf("%d + %d = %d\n", playerHand[0], playerHand[1], playerHand[0] + playerHand[1]);
		}
		printf("\nDealer:\n");
		if(dealerTotal > 21){	//Dealer busts, Player Wins!
			printf("%d + %d = %d, Dealer BUSTED!\n", dealerHand[0], dealerHand[1], dealerHand[0] + dealerHand[1]);
			printf("\nCongratulations! You Won!\n\n");
			return 0;
		}else if(dealerTotal >= 17){ //DEALER MUST STAND
			printf("%d + %d = %d, Dealer must Stand\n", dealerHand[0], dealerHand[1], dealerHand[0] + dealerHand[1]);
			dealerState = 1;
		}else{	//DEALER MUST HIT
			printf("? + %d, Dealer must Hit\n", dealerHand[1]);
			int cardToAdd = getValue();	//Need to check if this is possibly a second ace
			if(cardToAdd == 11){
				if(dealerAce == 1){
					cardToAdd = 1; //Cannot run 2 aces for 11
				}else{
					dealerAce = 1; //Dealer has an ace in play for 11 now
				}
			}
			dealerHand[1] = dealerHand[1] + cardToAdd;	//This can possibly result in a bust
			dealerTotal = dealerHand[0] + dealerHand[1];	
			if(dealerTotal > 21 && dealerAce == 1){ 	//Might be able to play an ace for 1 instead of 11
				if(dealerHand[0] == 11){	//Need to change the ace in the hole to a 1
					dealerHand[0] = 1;
				}else{
					dealerHand[1] = (dealerHand[1] - 11) + 1;	//Need to switch another 11 to a 1
					printf("Dealer converted an ace from 11 to 1\n");
				}
				dealerAce = 0;	//No longer have any aces in play for 11
				dealerTotal = dealerHand[0] + dealerHand[1];	//update total 
			}
		}
	}
	//Compare the two scores after both players have chosen 'Stand'
	printf("\nDealer and Player will Stand, Comparing Scores\n");
	printf("You: %d + %d = %d\n", playerHand[0], playerHand[1], playerHand[0] + playerHand[1]);
	printf("Dealer: %d + %d = %d\n", dealerHand[0], dealerHand[1], dealerHand[0] + dealerHand[1]);
	if(dealerTotal == playerTotal){
		printf("\nBoth players tied, result is a Push!\n\n");
		exit(0);
	}else if(dealerTotal > playerTotal){
		printf("\nDealer had a higher score than You, You Lose!\n");
		printf("\nBetter luck next time!\n\n");
		exit(0);
	}else{
		printf("\nCONGRATULATIONS! YOU WIN!\n\n");
		exit(0);
	}
}

//Generates a card number 0-12 and then converts it into a numerical blackjack value
int getValue(){
	int card = rand() % 13;	
	if(card == 12){	//ACE
		return 11;	
	}else if(card == 11 || card == 10 || card == 9){	// King/Queen/Jack
		return 10;
	}else{	//2-10
		return card + 2;
	}
}
