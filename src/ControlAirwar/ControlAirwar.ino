
#include <OneButton.h>  // Se incluye una libreria para leer el tiempo que dura el boton presionado

OneButton button(A1,true);   // Se define el puerto para la biblioteca

int pinButton = 10;    // Se define el pin para el boton
int pinLED = 11;       // Se define el pin para el LED
boolean PlaneDestroyed = false;  //Se inicializa la variable como false
void setup() {
  pinMode(pinButton,INPUT);   
  pinMode(pinLED,OUTPUT);
  button.attachLongPressStop(longclick); // Llama al metodo longclick cuando el boton se mantiene presionado
  button.attachClick(singleclick);  // Llama al metodo singleclick cuando el boton se presiona una vez
  Serial.begin(9600);
}

void loop() {
  button.tick();   //Lee el estado del boton 
  if(Serial.available() > 0){
    boolean PlaneDestroyed = Serial.read();
    Serial.print("Recibi: " + PlaneDestroyed);
  }
  if(PlaneDestroyed){
    LightLED();
  }
}
void LightLED(){
  digitalWrite(pinLED,HIGH);
  delay(100);
  digitalWrite(pinLED,LOW);
}

void longclick(){  
  double speed = 1;
  while(true){
    if (speed < 25) {
      speed += 0.25;
    }
  }
  Serial.print(speed);
  delay(5);
  
}
void singleclick(){
  Serial.print(1.25);  
}

