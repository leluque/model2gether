// Generated from G:\_TG\_Testes\Teste_ANTLR\Teste_3\src\u005CuseCaseLanguage\UseCaseDiagram.g4 by ANTLR 4.4
package br.com.dsls.useCaseDiagram;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link UseCaseDiagramParser}.
 */
public interface UseCaseDiagramListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link UseCaseDiagramParser#actor}.
	 * @param ctx the parse tree
	 */
	void enterActor(@NotNull UseCaseDiagramParser.ActorContext ctx);
	/**
	 * Exit a parse tree produced by {@link UseCaseDiagramParser#actor}.
	 * @param ctx the parse tree
	 */
	void exitActor(@NotNull UseCaseDiagramParser.ActorContext ctx);
	/**
	 * Enter a parse tree produced by {@link UseCaseDiagramParser#actors}.
	 * @param ctx the parse tree
	 */
	void enterActors(@NotNull UseCaseDiagramParser.ActorsContext ctx);
	/**
	 * Exit a parse tree produced by {@link UseCaseDiagramParser#actors}.
	 * @param ctx the parse tree
	 */
	void exitActors(@NotNull UseCaseDiagramParser.ActorsContext ctx);
	/**
	 * Enter a parse tree produced by {@link UseCaseDiagramParser#useCases}.
	 * @param ctx the parse tree
	 */
	void enterUseCases(@NotNull UseCaseDiagramParser.UseCasesContext ctx);
	/**
	 * Exit a parse tree produced by {@link UseCaseDiagramParser#useCases}.
	 * @param ctx the parse tree
	 */
	void exitUseCases(@NotNull UseCaseDiagramParser.UseCasesContext ctx);
	/**
	 * Enter a parse tree produced by {@link UseCaseDiagramParser#useCaseDiagram}.
	 * @param ctx the parse tree
	 */
	void enterUseCaseDiagram(@NotNull UseCaseDiagramParser.UseCaseDiagramContext ctx);
	/**
	 * Exit a parse tree produced by {@link UseCaseDiagramParser#useCaseDiagram}.
	 * @param ctx the parse tree
	 */
	void exitUseCaseDiagram(@NotNull UseCaseDiagramParser.UseCaseDiagramContext ctx);
	/**
	 * Enter a parse tree produced by {@link UseCaseDiagramParser#useCase}.
	 * @param ctx the parse tree
	 */
	void enterUseCase(@NotNull UseCaseDiagramParser.UseCaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link UseCaseDiagramParser#useCase}.
	 * @param ctx the parse tree
	 */
	void exitUseCase(@NotNull UseCaseDiagramParser.UseCaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link UseCaseDiagramParser#diagramName}.
	 * @param ctx the parse tree
	 */
	void enterDiagramName(@NotNull UseCaseDiagramParser.DiagramNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link UseCaseDiagramParser#diagramName}.
	 * @param ctx the parse tree
	 */
	void exitDiagramName(@NotNull UseCaseDiagramParser.DiagramNameContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relationComm}
	 * labeled alternative in {@link UseCaseDiagramParser#relation}.
	 * @param ctx the parse tree
	 */
	void enterRelationComm(@NotNull UseCaseDiagramParser.RelationCommContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationComm}
	 * labeled alternative in {@link UseCaseDiagramParser#relation}.
	 * @param ctx the parse tree
	 */
	void exitRelationComm(@NotNull UseCaseDiagramParser.RelationCommContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relationIncl}
	 * labeled alternative in {@link UseCaseDiagramParser#relation}.
	 * @param ctx the parse tree
	 */
	void enterRelationIncl(@NotNull UseCaseDiagramParser.RelationInclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationIncl}
	 * labeled alternative in {@link UseCaseDiagramParser#relation}.
	 * @param ctx the parse tree
	 */
	void exitRelationIncl(@NotNull UseCaseDiagramParser.RelationInclContext ctx);
	/**
	 * Enter a parse tree produced by {@link UseCaseDiagramParser#relations}.
	 * @param ctx the parse tree
	 */
	void enterRelations(@NotNull UseCaseDiagramParser.RelationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link UseCaseDiagramParser#relations}.
	 * @param ctx the parse tree
	 */
	void exitRelations(@NotNull UseCaseDiagramParser.RelationsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code elementActor}
	 * labeled alternative in {@link UseCaseDiagramParser#element}.
	 * @param ctx the parse tree
	 */
	void enterElementActor(@NotNull UseCaseDiagramParser.ElementActorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code elementActor}
	 * labeled alternative in {@link UseCaseDiagramParser#element}.
	 * @param ctx the parse tree
	 */
	void exitElementActor(@NotNull UseCaseDiagramParser.ElementActorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code elementUseCase}
	 * labeled alternative in {@link UseCaseDiagramParser#element}.
	 * @param ctx the parse tree
	 */
	void enterElementUseCase(@NotNull UseCaseDiagramParser.ElementUseCaseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code elementUseCase}
	 * labeled alternative in {@link UseCaseDiagramParser#element}.
	 * @param ctx the parse tree
	 */
	void exitElementUseCase(@NotNull UseCaseDiagramParser.ElementUseCaseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relationExte}
	 * labeled alternative in {@link UseCaseDiagramParser#relation}.
	 * @param ctx the parse tree
	 */
	void enterRelationExte(@NotNull UseCaseDiagramParser.RelationExteContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationExte}
	 * labeled alternative in {@link UseCaseDiagramParser#relation}.
	 * @param ctx the parse tree
	 */
	void exitRelationExte(@NotNull UseCaseDiagramParser.RelationExteContext ctx);
}