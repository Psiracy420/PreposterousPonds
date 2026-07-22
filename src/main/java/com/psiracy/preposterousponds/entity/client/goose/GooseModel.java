package com.psiracy.preposterousponds.entity.client.goose;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class GooseModel extends EntityModel<GooseRenderState>
{
    private final ModelPart Goose;
    private final ModelPart body;
    private final ModelPart left_wing;
    private final ModelPart right_wing;
    private final ModelPart left_leg;
    private final ModelPart right_leg;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart chin;
    private final ModelPart bill;

    private final KeyframeAnimation walkingAnimation;
    private final KeyframeAnimation idleAnimation;

    public GooseModel(ModelPart root)
    {
        super(root);
        this.Goose = root.getChild("SimpleGoose");
        this.body = this.Goose.getChild("body");
        this.left_wing = this.Goose.getChild("left_wing");
        this.right_wing = this.Goose.getChild("right_wing");
        this.left_leg = this.Goose.getChild("left_leg");
        this.right_leg = this.Goose.getChild("right_leg");
        this.neck = this.Goose.getChild("neck");
        this.head = this.neck.getChild("head");
        this.chin = this.head.getChild("chin");
        this.bill = this.chin.getChild("bill");

        this.walkingAnimation = GooseAnimations.walk.bake(root);
        this.idleAnimation = GooseAnimations.idle.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition SimpleGoose = partdefinition.addOrReplaceChild("SimpleGoose", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = SimpleGoose.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -5.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(24, 8).addBox(-2.5F, 5.0F, 1.0F, 5.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 14).addBox(-2.5F, 9.0F, 3.0F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition left_wing = SimpleGoose.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(32, 18).addBox(0.0F, 0.0F, 4.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -11.0F, 0.0F));

        PartDefinition right_wing = SimpleGoose.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(16, 16).addBox(0.0F, 0.0F, -3.0F, 1.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(12, 37).addBox(0.0F, 0.0F, 4.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -11.0F, 0.0F));

        PartDefinition left_leg = SimpleGoose.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(12, 28).addBox(-1.0F, 0.0F, -4.0F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -5.0F, 1.0F));

        PartDefinition right_leg = SimpleGoose.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(26, 28).addBox(-1.0F, 0.0F, -4.0F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -5.0F, 1.0F));

        PartDefinition neck = SimpleGoose.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 28).addBox(-2.5F, -11.0F, -5.0F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -6.0F, -1.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 0).addBox(-2.0F, -4.0F, -3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -11.0F, -3.0F));

        PartDefinition chin = head.addOrReplaceChild("chin", CubeListBuilder.create().texOffs(26, 37).addBox(-1.0F, -11.0F, -4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition bill = chin.addOrReplaceChild("bill", CubeListBuilder.create().texOffs(32, 25).addBox(-1.0F, -10.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 37).addBox(-1.0F, -9.0F, -6.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(GooseRenderState state)
    {
        this.root.getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(state.yRot, state.xRot);

        this.walkingAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 1f, 1f);
        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch)
    {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45f);

        this.head.yRot = headYaw * ((float) Math.PI/180f);
        this.head.xRot = headPitch * ((float) Math.PI/180f);
    }
}