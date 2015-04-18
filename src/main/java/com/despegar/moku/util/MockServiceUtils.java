package com.despegar.moku.util;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.despegar.moku.model.MockService;
import com.despegar.moku.model.MockServiceResponse;
import com.despegar.moku.model.RequestKeyField;
import com.despegar.moku.model.RequestKeyFieldValue;
import com.despegar.moku.model.enums.FieldType;
import com.fasterxml.jackson.core.JsonProcessingException;

public class MockServiceUtils {

	private static final Logger logger = LoggerFactory.getLogger(MockServiceUtils.class);

	public static Optional<MockServiceResponse> getOtherResponse(Object body, String[] pathVariables,
			Map<String, String[]> queryParameters, MockService mockService) throws JsonProcessingException {

		if (CollectionUtils.isEmpty(mockService.getOtherResponses())) {
			logger.info("No hay otra respuesta que devolver");
			return Optional.empty();
		}

		if (CollectionUtils.isEmpty(mockService.getRequestKeyFields())) {
			logger.info("No hay definidos field keys para elegir una respuesta alternativa");
			return Optional.empty();
		}

		for (MockServiceResponse response : mockService.getOtherResponses()) {

			boolean pathVariableApply = false;
			boolean queryParamsApply = false;
			boolean bodyApply = false;

			if (existsAnyValueOfType(response.getRequestKeyFieldValues(), mockService.getRequestKeyFields(), FieldType.PATH)) {
				if (filterRequestKeyFieldValues(response.getRequestKeyFieldValues(), mockService.getRequestKeyFields(), FieldType.PATH)
						.allMatch(
								value -> mockService
										.getRequestKeyFields()
										.stream()
										.anyMatch(
												field -> {
													boolean apply = field.getCode().equals(value.getRequestKeyFieldCode())
															&& value.getValue().equals(
																	getPathVariableValue(pathVariables, field.getPathVariableIndex()));
													if (field.getCode().equals(value.getRequestKeyFieldCode())) {
														logger.debug(
																"Codigo de field: {} :: Codigo de Value: {} :: value.getValue(): {} :: request value: {}",
																field.getCode(), value.getRequestKeyFieldCode(), value.getValue(),
																getPathVariableValue(pathVariables, field.getPathVariableIndex()));
													}
													return apply;
												}))) {
					pathVariableApply = true;
				}

			} else {
				pathVariableApply = true;
			}

			if (existsAnyValueOfType(response.getRequestKeyFieldValues(), mockService.getRequestKeyFields(), FieldType.QUERY)) {
				if (filterRequestKeyFieldValues(response.getRequestKeyFieldValues(), mockService.getRequestKeyFields(), FieldType.QUERY)
						.allMatch(
								value -> mockService
										.getRequestKeyFields()
										.stream()
										.anyMatch(
												field -> {
													boolean apply = field.getCode().equals(value.getRequestKeyFieldCode())
															&& CollectionUtils.arrayToList(queryParameters.get(field.getParamName())).contains(value.getValue());
													if (field.getCode().equals(value.getRequestKeyFieldCode())) {
														logger.debug(
																"Codigo de field: {} :: Codigo de Value: {} :: value.getValue(): {} :: request value: {}",
																field.getCode(), value.getRequestKeyFieldCode(), value.getValue(),
																queryParameters.get(field.getParamName()));
													}
													return apply;
												}))) {
					queryParamsApply = true;
				}
			} else {
				queryParamsApply = true;
			}

			if (existsAnyValueOfType(response.getRequestKeyFieldValues(), mockService.getRequestKeyFields(), FieldType.BODY)) {
				if (body != null) {
					String jsonBody = JsonUtils.writer().writeValueAsString(body);

					if (filterRequestKeyFieldValues(response.getRequestKeyFieldValues(), mockService.getRequestKeyFields(), FieldType.BODY)
							.allMatch(
									value -> mockService
											.getRequestKeyFields()
											.stream()
											.anyMatch(
													field -> {
														boolean apply = field.getCode().equals(value.getRequestKeyFieldCode())
																&& value.getValue().equals(
																		JsonUtils.getValue(jsonBody, field.getPathInJson())
																				.orElse(StringUtils.EMPTY).toString());
														if (field.getCode().equals(value.getRequestKeyFieldCode())) {
															logger.debug(
																	"Codigo de field: {} :: Codigo de Value: {} :: value.getValue(): {} :: request value: {}",
																	field.getCode(),
																	value.getRequestKeyFieldCode(),
																	value.getValue(),
																	JsonUtils.getValue(jsonBody, field.getPathInJson())
																	.orElse(StringUtils.EMPTY).toString());
														}
														return apply;
													}))) {
						bodyApply = true;
					}
				}
			} else {
				bodyApply = true;
			}

			if (pathVariableApply && queryParamsApply && bodyApply) {
				return Optional.of(response);
			}
		}

		return Optional.empty();
	}

	private static boolean existsAnyValueOfType(List<RequestKeyFieldValue> values, List<RequestKeyField> fields, FieldType fieldType) {
		return values.stream().anyMatch(
				value -> fields.stream().anyMatch(
						field -> field.getCode().equals(value.getRequestKeyFieldCode()) && fieldType.equals(field.getType())));
	}

	private static String getPathVariableValue(String[] pathVariables, Integer pathVariableIndex) {
		if (pathVariables.length <= 1) {
			return StringUtils.EMPTY;
		}

		if (pathVariableIndex >= pathVariables.length) {
			return StringUtils.EMPTY;
		}
		return pathVariables[pathVariableIndex];
	}

	private static Stream<RequestKeyFieldValue> filterRequestKeyFieldValues(List<RequestKeyFieldValue> requestKeyFieldValues,
			List<RequestKeyField> requestKeyFields, FieldType fieldType) {
		return requestKeyFieldValues.stream().filter(
				value -> requestKeyFields.stream().anyMatch(
						field -> fieldType.equals(field.getType()) && field.getCode().equals(value.getRequestKeyFieldCode())));
	}

}
