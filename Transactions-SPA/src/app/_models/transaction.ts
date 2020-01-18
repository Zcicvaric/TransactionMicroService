export class Transaction {
	id: number;
	uid: string;
	number: number;
	date: string;
	description: string;
	status: string;
	transactionType: string;
	paymentInstrument: string;
	transactionAmount: number;
	payerIBAN: string;
	payerCurrency: string;
	receiverIBAN: string;
	receiverCurrency: string;
	receiverExchangeRate: number;
	swiftCode: string;
	model: number;
	referenceNumber: string;
	usageCode: string;
	typeOfExpense: string;
}
