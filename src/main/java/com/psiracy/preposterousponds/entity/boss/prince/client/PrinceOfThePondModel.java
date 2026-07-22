package com.psiracy.preposterousponds.entity.boss.prince.client;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class PrinceOfThePondModel extends EntityModel<PrinceOfThePondRenderState>
{
    private final ModelPart POTP;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart tailStart;
    private final ModelPart tailMid;
    private final ModelPart tailPoint;
    private final ModelPart legR;
    private final ModelPart legL;
    private final ModelPart wingR;
    private final ModelPart outerWingR;
    private final ModelPart wingL;
    private final ModelPart outerWingL;
    private final ModelPart neck;
    private final ModelPart middleNeck;
    private final ModelPart topNeck;
    private final ModelPart head;
    private final ModelPart beak;
    private final ModelPart lowerBeak;

    private final KeyframeAnimation walkingAnimation;
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation slamAnimation;
    private final KeyframeAnimation honkAnimation;
    private final KeyframeAnimation runAnimation;
    private final KeyframeAnimation idletolaserAnimation;
    private final KeyframeAnimation laserAnimation;
    private final KeyframeAnimation wingFlapAnimation;

    public PrinceOfThePondModel(ModelPart root)
    {
        super(root);
        this.POTP = root.getChild("POTP");
        this.body = this.POTP.getChild("body");
        this.tail = this.body.getChild("tail");
        this.tailStart = this.tail.getChild("tailStart");
        this.tailMid = this.tailStart.getChild("tailMid");
        this.tailPoint = this.tailMid.getChild("tailPoint");
        this.legR = this.POTP.getChild("legR");
        this.legL = this.POTP.getChild("legL");
        this.wingR = this.POTP.getChild("wingR");
        this.outerWingR = this.wingR.getChild("outerWingR");
        this.wingL = this.POTP.getChild("wingL");
        this.outerWingL = this.wingL.getChild("outerWingL");
        this.neck = this.POTP.getChild("neck");
        this.middleNeck = this.neck.getChild("middleNeck");
        this.topNeck = this.middleNeck.getChild("topNeck");
        this.head = this.topNeck.getChild("head");
        this.beak = this.head.getChild("beak");
        this.lowerBeak = this.beak.getChild("lowerBeak");

        this.walkingAnimation = PrinceOfThePondAnimations.walk.bake(root);
        this.idleAnimation = PrinceOfThePondAnimations.idle.bake(root);
        this.slamAnimation = PrinceOfThePondAnimations.neck_slam.bake(root);
        this.honkAnimation = PrinceOfThePondAnimations.honk.bake(root);
        this.runAnimation = PrinceOfThePondAnimations.run.bake(root);
        this.idletolaserAnimation = PrinceOfThePondAnimations.idle_to_laser.bake(root);
        this.laserAnimation = PrinceOfThePondAnimations.laser.bake(root);
        this.wingFlapAnimation = PrinceOfThePondAnimations.wing_flap.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition POTP = partdefinition.addOrReplaceChild("POTP", CubeListBuilder.create(), PartPose.offset(1.0F, 24.3628F, -0.1294F));

        PartDefinition body = POTP.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(10.0F, -26.3628F, -22.8706F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(152, 219).addBox(-10.0F, -15.0F, -1.0F, 0.0F, 15.0F, 23.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -21.0F, 27.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-21.0F, -26.0F, -1.0F, 22.0F, 26.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-11.0F, -6.3769F, 44.6277F));

        PartDefinition cube_r3 = tail.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(82, 235).addBox(-8.0F, -36.0F, 0.0F, 0.0F, 12.0F, 18.0F, new CubeDeformation(0.1F))
                .texOffs(94, 128).addBox(-19.0F, -24.0F, -1.0F, 20.0F, 24.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, 10.3769F, 3.3723F, -0.0873F, 0.0F, 0.0F));

        PartDefinition tailStart = tail.addOrReplaceChild("tailStart", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.069F, 19.2054F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r4 = tailStart.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(174, 128).addBox(-6.0F, -34.0F, 1.0F, 0.0F, 14.0F, 28.0F, new CubeDeformation(0.1F))
                .texOffs(102, 74).addBox(-15.0F, -20.0F, -3.0F, 16.0F, 20.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 11.4459F, 4.1669F, -0.0873F, 0.0F, 0.0F));

        PartDefinition tailMid = tailStart.addOrReplaceChild("tailMid", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 6.1044F, 33.5093F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r5 = tailMid.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(140, 0).addBox(-4.0F, -28.0F, 1.0F, 0.0F, 14.0F, 33.0F, new CubeDeformation(0.1F))
                .texOffs(0, 74).addBox(-11.0F, -14.0F, -3.0F, 12.0F, 14.0F, 39.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 6.3415F, 2.6576F, -0.0873F, 0.0F, 0.0F));

        PartDefinition tailPoint = tailMid.addOrReplaceChild("tailPoint", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 3.1067F, 38.2989F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r6 = tailPoint.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 127).addBox(-7.0F, -8.0F, 34.0F, 8.0F, 8.0F, 39.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 1.2348F, -35.6412F, -0.0873F, 0.0F, 0.0F));

        PartDefinition legR = POTP.addOrReplaceChild("legR", CubeListBuilder.create().texOffs(238, 247).addBox(-3.0F, -2.3333F, -4.6667F, 6.0F, 10.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(202, 271).addBox(-2.0F, 7.6667F, -1.6667F, 4.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(228, 40).addBox(-4.0F, 23.6667F, -11.6667F, 8.0F, 0.0F, 18.0F, new CubeDeformation(0.1F)), PartPose.offset(5.0F, -24.0295F, 2.796F));

        PartDefinition legL = POTP.addOrReplaceChild("legL", CubeListBuilder.create().texOffs(250, 217).addBox(-3.0F, -2.3333F, -4.6667F, 6.0F, 10.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(38, 272).addBox(-2.0F, 7.6667F, -1.6667F, 4.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 235).addBox(-4.0F, 23.6667F, -11.6667F, 8.0F, 0.0F, 18.0F, new CubeDeformation(0.1F)), PartPose.offset(-5.0F, -24.0295F, 2.796F));

        PartDefinition wingR = POTP.addOrReplaceChild("wingR", CubeListBuilder.create().texOffs(118, 235).addBox(-11.8721F, -3.125F, -6.9833F, 12.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(182, 170).addBox(-29.8721F, -2.125F, -5.9833F, 30.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.1279F, -43.2378F, -5.8873F));

        PartDefinition cube_r7 = wingR.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(270, 139).addBox(-14.0F, -2.0F, -1.0F, 15.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.8721F, 1.875F, 1.0167F, 0.0F, 1.7453F, 0.0F));

        PartDefinition cube_r8 = wingR.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(250, 239).addBox(-19.0F, -2.0F, -1.0F, 20.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.8721F, 1.875F, 1.0167F, 0.0F, 1.7017F, 0.0F));

        PartDefinition cube_r9 = wingR.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(110, 269).addBox(-18.0F, -2.0F, -1.0F, 19.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.8721F, 1.875F, 1.0167F, 0.0F, 1.7017F, 0.0F));

        PartDefinition cube_r10 = wingR.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(228, 186).addBox(-23.0F, -2.0F, -1.0F, 24.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.8721F, 1.875F, 1.0167F, 0.0F, 1.7017F, 0.0F));

        PartDefinition cube_r11 = wingR.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 227).addBox(-28.0F, -2.0F, -1.0F, 29.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-26.8721F, 1.875F, 1.0167F, 0.0F, 1.6581F, 0.0F));

        PartDefinition outerWingR = wingR.addOrReplaceChild("outerWingR", CubeListBuilder.create().texOffs(142, 194).addBox(-72.0449F, -2.0385F, -3.5692F, 36.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(140, 47).addBox(-36.0449F, -3.0385F, -4.5692F, 36.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-29.8272F, 0.9135F, -2.4141F));

        PartDefinition cube_r12 = outerWingR.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(202, 78).addBox(-35.0F, -2.0F, -1.0F, 36.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-35.0449F, 0.9615F, 3.4308F, 0.0F, 0.0873F, 0.0F));

        PartDefinition cube_r13 = outerWingR.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(202, 86).addBox(-35.0F, -2.0F, -1.0F, 36.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-32.0449F, 1.9615F, 2.4308F, 0.0F, 0.3927F, 0.0F));

        PartDefinition cube_r14 = outerWingR.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(206, 32).addBox(-33.0F, -2.0F, -1.0F, 34.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-30.0449F, 0.9615F, -0.5692F, 0.0F, 0.6545F, 0.0F));

        PartDefinition cube_r15 = outerWingR.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(202, 94).addBox(-35.0F, -2.0F, -1.0F, 36.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-29.0449F, -0.0385F, 3.4308F, 0.0F, 0.829F, 0.0F));

        PartDefinition cube_r16 = outerWingR.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(202, 102).addBox(-35.0F, -2.0F, -1.0F, 36.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-27.0449F, 1.9615F, 2.4308F, 0.0F, 1.309F, 0.0F));

        PartDefinition cube_r17 = outerWingR.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(140, 61).addBox(-36.0F, -2.0F, -1.0F, 37.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-27.0449F, 0.9615F, 2.4308F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r18 = outerWingR.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(206, 0).addBox(-34.0F, -2.0F, -1.0F, 35.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-26.0449F, 0.9615F, 1.4308F, 0.0F, 1.5272F, 0.0F));

        PartDefinition cube_r19 = outerWingR.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(80, 211).addBox(-32.0F, -2.0F, -1.0F, 33.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.0449F, -0.0385F, 1.4308F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r20 = outerWingR.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(206, 8).addBox(-34.0F, -2.0F, -1.0F, 35.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.0449F, 0.9615F, 1.4308F, 0.0F, 1.6144F, 0.0F));

        PartDefinition cube_r21 = outerWingR.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(226, 61).addBox(-29.0F, -2.0F, -1.0F, 30.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0449F, 1.9615F, 1.4308F, 0.0F, 1.6144F, 0.0F));

        PartDefinition cube_r22 = outerWingR.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(0, 219).addBox(-31.0F, -2.0F, -1.0F, 32.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0449F, 0.9615F, 3.4308F, 0.0F, 1.6144F, 0.0F));

        PartDefinition wingL = POTP.addOrReplaceChild("wingL", CubeListBuilder.create().texOffs(270, 126).addBox(-0.1279F, -3.125F, -6.9833F, 12.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(62, 186).addBox(-0.1279F, -2.125F, -5.9833F, 30.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(9.1279F, -43.2378F, -5.8873F));

        PartDefinition cube_r23 = wingL.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(270, 145).addBox(-1.0F, -2.0F, -1.0F, 15.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.8721F, 1.875F, 1.0167F, 0.0F, -1.7453F, 0.0F));

        PartDefinition cube_r24 = wingL.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(262, 166).addBox(-1.0F, -2.0F, -1.0F, 20.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.8721F, 1.875F, 1.0167F, 0.0F, -1.7017F, 0.0F));

        PartDefinition cube_r25 = wingL.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(238, 269).addBox(-1.0F, -2.0F, -1.0F, 19.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.8721F, 1.875F, 1.0167F, 0.0F, -1.7017F, 0.0F));

        PartDefinition cube_r26 = wingL.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(230, 160).addBox(-1.0F, -2.0F, -1.0F, 24.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.8721F, 1.875F, 1.0167F, 0.0F, -1.7017F, 0.0F));

        PartDefinition cube_r27 = wingL.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(70, 227).addBox(-1.0F, -2.0F, -1.0F, 29.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(26.8721F, 1.875F, 1.0167F, 0.0F, -1.6581F, 0.0F));

        PartDefinition outerWingL = wingL.addOrReplaceChild("outerWingL", CubeListBuilder.create().texOffs(202, 69).addBox(36.0449F, -2.0385F, -3.5692F, 36.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(94, 172).addBox(0.0449F, -3.0385F, -4.5692F, 36.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(29.8272F, 0.9135F, -2.4141F));

        PartDefinition cube_r28 = outerWingL.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(146, 203).addBox(-1.0F, -2.0F, -1.0F, 36.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(35.0449F, 0.9615F, 3.4308F, 0.0F, -0.0873F, 0.0F));

        PartDefinition cube_r29 = outerWingL.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(62, 203).addBox(-1.0F, -2.0F, -1.0F, 36.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(32.0449F, 1.9615F, 2.4308F, 0.0F, -0.3927F, 0.0F));

        PartDefinition cube_r30 = outerWingL.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(0, 211).addBox(-1.0F, -2.0F, -1.0F, 34.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(30.0449F, 0.9615F, -0.5692F, 0.0F, -0.6545F, 0.0F));

        PartDefinition cube_r31 = outerWingL.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(202, 118).addBox(-1.0F, -2.0F, -1.0F, 36.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(29.0449F, -0.0385F, 3.4308F, 0.0F, -0.829F, 0.0F));

        PartDefinition cube_r32 = outerWingL.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(202, 110).addBox(-1.0F, -2.0F, -1.0F, 36.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(27.0449F, 1.9615F, 2.4308F, 0.0F, -1.309F, 0.0F));

        PartDefinition cube_r33 = outerWingL.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(142, 186).addBox(-1.0F, -2.0F, -1.0F, 37.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(27.0449F, 0.9615F, 2.4308F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r34 = outerWingL.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(206, 24).addBox(-1.0F, -2.0F, -1.0F, 35.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(26.0449F, 0.9615F, 1.4308F, 0.0F, -1.5272F, 0.0F));

        PartDefinition cube_r35 = outerWingL.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(158, 211).addBox(-1.0F, -2.0F, -1.0F, 33.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0449F, -0.0385F, 1.4308F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r36 = outerWingL.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(206, 16).addBox(-1.0F, -2.0F, -1.0F, 35.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0449F, 0.9615F, 1.4308F, 0.0F, -1.6144F, 0.0F));

        PartDefinition cube_r37 = outerWingL.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(226, 194).addBox(-1.0F, -2.0F, -1.0F, 30.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0449F, 1.9615F, 1.4308F, 0.0F, -1.6144F, 0.0F));

        PartDefinition cube_r38 = outerWingL.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(76, 219).addBox(-1.0F, -2.0F, -1.0F, 32.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0449F, 0.9615F, 3.4308F, 0.0F, -1.6144F, 0.0F));

        PartDefinition neck = POTP.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 174).addBox(-8.0F, -12.0F, -7.1667F, 16.0F, 22.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(52, 235).addBox(0.0F, -12.0F, 7.8333F, 0.0F, 22.0F, 15.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -51.3628F, -20.7039F, 0.2182F, 0.0F, 0.0F));

        PartDefinition middleNeck = neck.addOrReplaceChild("middleNeck", CubeListBuilder.create().texOffs(198, 247).addBox(-5.0F, -16.0F, -4.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(182, 271).addBox(0.0F, -16.0F, 6.0F, 0.0F, 14.0F, 10.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -8.0F, -0.1667F, 0.1745F, 0.0F, 0.0F));

        PartDefinition topNeck = middleNeck.addOrReplaceChild("topNeck", CubeListBuilder.create().texOffs(230, 126).addBox(-5.0F, -25.0F, -4.0F, 10.0F, 24.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(162, 257).addBox(0.0F, -25.0F, 6.0F, 0.0F, 24.0F, 10.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -14.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition head = topNeck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(198, 219).addBox(-7.0F, -10.8333F, -8.6667F, 12.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(82, 265).addBox(-1.0F, -21.8333F, -6.6667F, 0.0F, 11.0F, 14.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(1.0F, -24.4667F, -0.8849F, -0.6109F, 0.0F, 0.0F));

        PartDefinition beak = head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(118, 257).addBox(-7.0F, -1.5283F, -12.7813F, 14.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(236, 202).addBox(-5.5F, -0.5283F, -24.7813F, 11.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -1.305F, 0.1147F));

        PartDefinition cube_r39 = beak.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(62, 174).addBox(-5.0F, -0.5F, -9.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 266).addBox(-5.0F, -0.5F, -4.5F, 10.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.5173F, -9.1727F, 0.5672F, 0.0F, 0.0F));

        PartDefinition lowerBeak = beak.addOrReplaceChild("lowerBeak", CubeListBuilder.create().texOffs(0, 253).addBox(-5.5F, 0.75F, -19.25F, 11.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(262, 172).addBox(-6.5F, 0.75F, -8.25F, 13.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.7217F, -4.5313F));

        return LayerDefinition.create(meshdefinition, 512, 512);
    }

    @Override
    public void setupAnim(PrinceOfThePondRenderState state)
    {
        this.root.getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(state.yRot, state.xRot);

        this.walkingAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 1f, 1f);
        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks, 1f);
        this.slamAnimation.apply(state.slamAnimationState, state.ageInTicks, 1f);
        this.honkAnimation.apply(state.honkAnimationState, state.ageInTicks, 1f);
        this.runAnimation.apply(state.runAnimationState, state.ageInTicks, 1f);
        this.idletolaserAnimation.apply(state.idletolaserAnimationState, state.ageInTicks, 1f);
        this.laserAnimation.apply(state.laserAnimationState, state.ageInTicks, 1f);
        this.wingFlapAnimation.apply(state.wingFlapAnimationState, state.ageInTicks, 1.5f);
    }

    private void applyHeadRotation(float headYaw, float headPitch)
    {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45f);

        this.head.yRot = headYaw * ((float) Math.PI/180f);
        this.head.xRot = headPitch * ((float) Math.PI/180f);
    }
}
