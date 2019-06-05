************services project************
****AccountHoldController.java****

public static final String ACCOUNT_HOLD_UPLOAD = "/accounthold/upload";

@PostMapping(ACCOUNT_HOLD_UPLOAD)
public List<AccountHold> uploadAccountHolds(@Valid @RequestBody AccountHoldUpload accountHoldUpload) throws EntityNotFoundException, EditErrorException {
	log.debug("*************************** uploadAccountHolds(),accountHoldUpload);
	List<AccountHold> accountHolds = accountHoldService.uploadAccountHoIds(accountHoldUpload);
	return accountHolds;
}
	
*************UI Project****************
****Utility.java****

public static List<String> readExcel(String filePath) throws IOException {
	List<String> returnValue = new ArrayList<>();
	try (FileInputStream excelFile = new FileInputStream(new File(filePath)));
		Workbook workbook = new XSSWorkbook(excelFile)) {
		Sheet datatypeSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator	 = datatypeSheet.iterator();
		while(iterator.hasNext()) {
			Row currentRow = iterator.next();
			Iterator<Call> cellIterator = currentRow.iterator();
			if(cellIterator.hasNext()) {
				Cell currentCell = cellIterator.next();
				if(currentCell.getCelType() == CellType.NUMERIC) {
					returnValue.add(Integer.toString((int) currentCell.getNumericCellValue()));
				}else if(currentCell.getCelType() == CellType.STRING) {
					returnValue.add(currentCell.getStringCellValue());
				}
			}
		}
	}catch(Exception ex){
		log.error("Exception occured while reading excel file---> ",ex);
	}
	return returnValue;
}

****AccountHoldController.java****
@PostMapping("/accounthold/upload")
public String uploadAccountHolds(Model model,@Valid @RequestBody String filePath,@Valid @RequestBody String accountHoldGroupName,RedirectAttributes rdr) throws EntityNotFoundException, EditErrorException {
	// TODO need to change to AccountHoldUploadResponse
	List<String> returnValue = Utility.readExcel(filePath);
	AccountHoldUpload accountHoldUpload = AccountHoldUpload.builder()
												.uploadFilename(new File(filePath).getName())
												.accountHoldGroupName(accountHoldGroupName)
												.acctNumbers(returnValue)
												.build();
	List<AccountHold> accountHolds = accountHoldService.uploadAccountHoIds(accountHoldUpload);
	model.addAttributes("accountHoldList",accountHolds);
	retun ACCOUNT_HOLD_DASHBOARD;
}

****AccountHoldService.java****

public List<AccountHold> uploadAccountHoIds(AccountHoldUpload accountHoldUpload);

****AccountHoldServiceImpl.java****

@Override
public List<AccountHold> uploadAccountHoIds(AccountHoldUpload accountHoldUpload) {
	String URL = baseUrl + "/accounthold/upload";
	ResponseEntity<String> svcResponse = restTemplateFactory.getRestTemplate().exchange(URL,HttpMethod.POST,null,String.class,accountHoldUpload);
	List<String> returnValue = new ArrayList<>();
	// TODO returnValue.addAll(parser.parseUploadAccountHoldsResponse());
	return returnValue;
}



"Ctrl + Shift + O" - Organize imports	
