package com.emv.qrcode.validators.mpm;

import static br.com.fluentvalidator.function.FunctionBuilder.of;
import static br.com.fluentvalidator.predicate.CollectionPredicate.empty;
import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.isNumeric;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEquals;
import static br.com.fluentvalidator.predicate.StringPredicate.stringSize;
import static br.com.fluentvalidator.predicate.StringPredicate.stringSizeLessThanOrEqual;

import com.emv.qrcode.core.model.TagLengthString;
import com.emv.qrcode.model.mpm.PaymentSystemSpecific;
import com.emv.qrcode.model.mpm.constants.PaymentSystemSpecificFieldCodes;

import br.com.fluentvalidator.AbstractValidator;

// @formatter:off
class PaymentSystemSpecificValidator extends AbstractValidator<PaymentSystemSpecific> {

  @Override
  public void rules() {

    ruleFor("GloballyUniqueIdentifier", PaymentSystemSpecific::getGloballyUniqueIdentifier)

      .must(not(stringEmptyOrNull(TagLengthString::getTag)))
        .withMessage("GloballyUniqueIdentifier tag is mandatory")
        .withAttempedValue(of(PaymentSystemSpecific::getGloballyUniqueIdentifier).andThen(TagLengthString::getTag))
        .critical()

      .must(stringSize(TagLengthString::getTag, 2))
        .withMessage("GloballyUniqueIdentifier tag must be size equal two")
        .withAttempedValue(of(PaymentSystemSpecific::getGloballyUniqueIdentifier).andThen(TagLengthString::getTag))
        .critical()

      .must(isNumeric(TagLengthString::getTag))
        .withMessage("GloballyUniqueIdentifier tag must be number")
        .withAttempedValue(of(PaymentSystemSpecific::getGloballyUniqueIdentifier).andThen(TagLengthString::getTag))
        .critical()

      .must(stringEquals(TagLengthString::getTag, PaymentSystemSpecificFieldCodes.ID_GLOBALLY_UNIQUE_IDENTIFIER))
        .withMessage(String.format("GloballyUniqueIdentifier tag must be '%s'", PaymentSystemSpecificFieldCodes.ID_GLOBALLY_UNIQUE_IDENTIFIER))
        .withAttempedValue(of(PaymentSystemSpecific::getGloballyUniqueIdentifier).andThen(TagLengthString::getTag))
        .critical()

      .must(not(stringEmptyOrNull(TagLengthString::getValue)))
        .withMessage("GloballyUniqueIdentifier value is mandatory")
        .withAttempedValue(of(PaymentSystemSpecific::getGloballyUniqueIdentifier).andThen(TagLengthString::getValue))
        .critical()

      .must(stringSizeLessThanOrEqual(TagLengthString::getValue, 32))
        .withMessage("GloballyUniqueIdentifier value must less then or equal size thirty-two")
        .withAttempedValue(of(PaymentSystemSpecific::getGloballyUniqueIdentifier).andThen(TagLengthString::getValue))
        .critical();

    ruleForEach(PaymentSystemSpecific::getPaymentSystemSpecific)
      .whenever(not(empty()))
        .withValidator(new TagLengthStringValidator("PaymentSystemSpecific.PaymentSystemSpecific", "01", "99", 99));
  }

}

// @formatter:on
