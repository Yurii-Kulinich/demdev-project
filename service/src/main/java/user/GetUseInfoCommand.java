package user;

import utils.Calculator;

public class GetUseInfoCommand {

  Calculator calculator = new Calculator();

  int getUserBmi(int height, int weight) {
    return weight / calculator.multiply(height, height);
  }

}
